package com.spring.security.server.browser;

import com.spring.security.server.browser.handler.SecurityAuthenticationFailureHandler;
import com.spring.security.server.browser.handler.SecurityAuthenticationSuccessHandler;
import com.spring.security.validate.filter.ValidateCodeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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

        //自定义验证码过滤器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(securityAuthenticationFailureHandler);

        http.csrf().disable()
            .authorizeRequests()
            .antMatchers(new String[]{"/login", "/login/authentication", "/login/verification/code"}).permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
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

    /**
     * @desc 记住我
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        return tokenRepository;
    }

}
