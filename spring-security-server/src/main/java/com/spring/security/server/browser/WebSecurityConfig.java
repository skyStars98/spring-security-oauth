package com.spring.security.server.browser;

import com.spring.security.server.browser.handler.SecurityAuthenticationFailureHandler;
import com.spring.security.server.browser.handler.SecurityAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/3
 * @Time: 2:02 下午
 * @Version: spring-security-oauth 1.0
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler;

    @Resource
    private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers(new String[]{"/login", "/login/authentication", "/login/verification/code"}).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login/authentication")
            .successHandler(securityAuthenticationSuccessHandler)
            .failureHandler(securityAuthenticationFailureHandler);
    }

    /**
     * @desc spring security 5不需要配置密码的加密方式，而是用户密码加前缀的方式表明加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
