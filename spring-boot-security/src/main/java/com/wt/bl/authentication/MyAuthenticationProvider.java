package com.wt.bl.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author WangTao
 *         Created at 18/10/8 下午3:54.
 */
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        authentication.getPrincipal();
        // 用户名
        String userName = authentication.getName();
        //
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 这个返回时什么意思，看源码
        /**
         * 如果这个认证提供者支持象征认证对象，返回ture
         * 返回ture不能保证认证提供者能够认证这个认证类提供的实例，只能表明它可以支持对其进行更加严谨的评估
         * 认证提供者也可以从一个认证方法返回一个null，表明另外一个认证提供者应该被使用
         */
        return true;
    }
}
