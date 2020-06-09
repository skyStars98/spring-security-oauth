package com.spring.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-06
 * @Time: 17:54
 * @Version: spring-security-oauth 1.0
 */
//@Configuration
//@EnableResourceServer //资源服务器
public class AuthorizationResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(new String[]{"/login", "/login/**"}).permitAll()
                .anyRequest().authenticated();
    }
}
