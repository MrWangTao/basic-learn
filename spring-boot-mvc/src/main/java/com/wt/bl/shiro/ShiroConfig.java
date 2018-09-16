package com.wt.bl.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.cache.config.CacheManagementConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author WangTao
 *         Created at 18/9/5 下午2:04.
 */
@Configuration
public class ShiroConfig {

    /**
     * LifecycleBeanPostProcessor，DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     * shiro的生命周期管理
     *
     * @return
     */
    /*@Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }*/

    /**
     * 对密码进行加密
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置迭代次数
        matcher.setHashIterations(2);
        // 设置加密方式
        matcher.setHashAlgorithmName("MD5");
        /**
         * 表示当前系统存储凭证散列是否为16进制。
         * 默认为 true 表示从Hex（16进制）解码凭证； false 表示从Base64解码系统凭据
         */
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    /**
     * 设置自定义shiro处理授权认证的方法
     *
     * @return
     */
    @Bean
    public MyShiroRealm1 myShiroRealm() {
        MyShiroRealm1 realm = new MyShiroRealm1();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    /**
     * 安全管理器，用于管理登录， 登出，权限， shiro session的管理
     * 可以设置 我们使用哪种缓存管理
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        // 如果一旦配置了sessionmanager就导致即使登录成功也无法访问要授权的页面
        // 这里需要使用 DefaultWebSessionManager类， 否则就会上述错误
        // securityManager.setSessionManager(sessionManager());
        securityManager.setSessionManager(webSessionManager());
        return securityManager;
    }

    /**
     * 工厂类，生成ShiroFilter
     * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager());

        // TODO 这部分应该可以在MyShiroRealm中判断是否有权限
        // 可以实现一些自定义的过滤器
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        filters.put("kickout", kickoutSessionControlFilter());
        /*LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        filters.put("logout", null);
        filters.put("authc", new MyFormFilter());*/
        factoryBean.setFilters(filters);

        Map<String, String> chainFilters = new LinkedHashMap<String, String>();
        chainFilters.put("/logout", "logout");
//        chainFilters.put("/user/**", "authc");
//        chainFilters.put("/user/**", "authc,roles[ROLE_USER]");
//        chainFilters.put("/**", "anon");
        chainFilters.put("/login", "anon");
        chainFilters.put("/index", "anon");
        chainFilters.put("/**", "authc,kickout");
        factoryBean.setSuccessUrl("/index");
        factoryBean.setUnauthorizedUrl("/403");
        factoryBean.setLoginUrl("/login_page");
        factoryBean.setFilterChainDefinitionMap(chainFilters);
        return factoryBean;
    }

    /**
     * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
     */
    /*@Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }*/

    /**
     * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
     * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
     */
    /*@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }*/

    /**
     * shiro中sessionManager是专门作会话管理的，而sessinManager将会话保存在sessionDAO中，如果不给sessionManager注入
     * sessionDAO，会话将是瞬时状态，没有被保存起来，从sessionManager里取session,是取不到的。
     *
     * SessionManager 会话管理器管理着应用中所有主体 `Subject` 的会话的创建、维护、删除、失效、验证等工作。 可用于同时只限定一个登陆，后续的剔除
     * 1：DefaultSessionManager： DefaultSecurityManager使用的默认实现，用于JavaSE环境，可以自定义
     * 2：ServletContainerSessionManager: DefaultWebSecurityManager使用的默认实现，用于web环境，其直接使用Servlet容器的会话
     * 3：DefaultWebSessionManager：用于Web环境的实现，可以替代ServletContainerSessionManager，自己维护着会话，直接 **废弃了** Servlet容器的会话管理。
     *
     */
    /*@Bean
    public DefaultSessionManager sessionManager() {
//        DefaultSessionManager sessionManager = new DefaultSessionManager();
//        // 设置过期时间: 单位ms， 默认设置的30分钟
//        sessionManager.setGlobalSessionTimeout(3600000);
//        // 设置删除无效的session
//        sessionManager.setDeleteInvalidSessions(true);
//        //
//        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
//        // 设置会话id生成器
//        enterpriseCacheSessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
//        enterpriseCacheSessionDAO.setActiveSessionsCacheName("active-shiro");
//        sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        return new DefaultSessionManager();
    }*/

    /**
     * 只所以出现这个问题是因为在shiro的DefaultWebSessionManager类中，默认Cookie名称是JSESSIONID，
     * 这样的话与servlet容器名冲突, 如jetty, tomcat等默认JSESSIONID,
     * 当跳出shiro servlet时如error-page容器会为JSESSIONID重新分配值导致登录会话丢失!
     * 如果不进行配置就会出现以下错误
     * org.apache.shiro.session.UnknownSessionException: There is no session with id [xxxx]
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager webSessionManager() {
        DefaultWebSessionManager webSessionManager = new DefaultWebSessionManager();
        webSessionManager.setSessionIdCookie(simpleCookie());
        return webSessionManager;
    }

    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("shiro-cookie");
        simpleCookie.setPath("/");
        return simpleCookie;
    }

    @Bean
    public KickOutSessionControllFilter kickoutSessionControlFilter(){
        KickOutSessionControllFilter kickoutSessionControlFilter = new KickOutSessionControllFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
        kickoutSessionControlFilter.setCache(cacheManager());
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionControlFilter.setSessionManager(webSessionManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        kickoutSessionControlFilter.setKickoutUrl("/login_page");
        return kickoutSessionControlFilter;
    }

    /**
     * 设置缓存管理器，可以是cache、redis等缓存
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager() {
        return new RedisManager();
    }



}


