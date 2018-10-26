package com.wt.bl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author WangTao
 *         Created at 18/10/8 下午2:17.
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(null);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()    // 授权请求
                .anyRequest()       // 任何请求
                .authenticated()    // 认证
                .and()
                .formLogin()        // 表单登录允许所有的请求
                .permitAll()
                .and()
                .logout()
                .and()
                .csrf().disable();  // 防止恶意攻击
    }




}
