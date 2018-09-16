package com.wt.bl.shiro;

import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 并发控制当前登录的个数
 * @author WangTao
 *         Created at 18/9/16 下午12:55.
 */
@Setter
public class KickOutSessionControllFilter extends AccessControlFilter {

    //踢出后到的地址
    private String kickoutUrl;

    //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private boolean kickoutAfter = false;

    //同一个帐号最大会话数 默认1
    private int maxSession = 1;

    private SessionManager sessionManager;

    private Cache<String, Deque<Serializable>> cache;

    public void setCache(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 当前登录的主体
        Subject subject = getSubject(servletRequest, servletResponse);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }
        // 当前登录的会话
        Session session = subject.getSession();
        String username = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();

        // ======================= 这部分是可以使用内存，NoSql或者数据库，个人觉得NoSql好一点， 欢迎看到的指正 ========================
        //同步控制： 双向队列 Deque
        Deque<Serializable> deque = cache.get(username);
//         cache.remove(username);
        if (deque == null) {
            deque = new LinkedList<Serializable>();
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
            cache.put(username, deque);
        }
        // ======================= 这部分是可以使用内存，NoSql或者数据库，个人觉得NoSql好一点， 欢迎看到的指正 ========================

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
                cache.put(username, deque);
            } catch (Exception e) {//ignore exception
                e.printStackTrace();
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) {
            }
            // 保存当前请求， 包含路径等一些信息
            saveRequest(servletRequest);
            WebUtils.issueRedirect(servletRequest, servletResponse, kickoutUrl);
            return false;
        }

        return true;

    }
}
