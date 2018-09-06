package com.wt.bl.shiro;

import com.wt.bl.entity.User;
import com.wt.bl.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author WangTao
 *         Created at 18/9/5 下午2:07.
 */
public class MyShiroRealm1 extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyShiroRealm1.class);

    @Autowired
    UserService userService;

    /**
     * 授权方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    /**
     * 认证方法
     * 当使用SecurityUtils.getSubject().login(token)时进行拦截验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("【doGetAuthenticationInfo】{}", authenticationToken);
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByUsername(token.getUsername());
        if (!Objects.isNull(user)) {
//            if (new SimpleHash("MD5", token.getPassword(), "111", 2).toString().equals(user.getPassword())) {
                /**
                 * SecurityUtils shiro核心
                 */
//                Session session = SecurityUtils.getSubject().getSession();
//                session.setAttribute(user.getUsername(), user);
                SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), new SimpleByteSource(user.getSalt().getBytes()), getName());
//                SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
                super.clearCachedAuthorizationInfo(authenticationInfo.getPrincipals());
                SecurityUtils.getSubject().getSession().setAttribute("login", user);
                return authenticationInfo;
//            } else {
//                throw new UnknownAccountException("用户名密码错误");
//            }

        }
        return null;
    }
}
