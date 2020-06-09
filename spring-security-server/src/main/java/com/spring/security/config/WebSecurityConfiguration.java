package com.spring.security.config;

import com.spring.security.mode.mobile.MobileAuthenticationFilter;
import com.spring.security.mode.mobile.MobileAuthenticationProvider;
import com.spring.security.handler.SecurityAuthenticationFailureHandler;
import com.spring.security.handler.SecurityAuthenticationSuccessHandler;
import com.spring.security.validate.filter.ValidateCodeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/3
 * @Time: 2:02 下午
 * @Version: spring-security-oauth 1.0
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler;

    @Resource
    private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;

    @Resource
    private SessionInformationExpiredStrategy authenticationExpiredSessionStrategy;

    @Resource
    private MobileAuthenticationProvider mobileAuthenticationProvider;

    @Resource
    private LogoutSuccessHandler authenticationLogoutSuccessHandler;

    @Resource
    private UserDetailsService userDetailsServiceImpl;

    @Resource
    private DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //自定义验证码过滤器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(securityAuthenticationFailureHandler);
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class); //将验证码过滤器加到用户过滤器前面

        //手机登录
        http.authenticationProvider(mobileAuthenticationProvider) //将手机登录的mobileAuthenticationProvider添加到AuthenticationManager管理的集合中
            .addFilterAfter(mobileAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(new String[]{"/login", "/login/**"}).permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/authentication")
                .successHandler(securityAuthenticationSuccessHandler)
                .failureHandler(securityAuthenticationFailureHandler)
                .and()
            .logout()
                .logoutUrl("/signOut")
                .logoutSuccessHandler(authenticationLogoutSuccessHandler)
                .deleteCookies("JSESSIONID") // 退出成功后删除指定的cookie
                .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(3600) //记住我的有效时间为一小时 单位秒
                .userDetailsService(userDetailsServiceImpl)
                .and()
            .sessionManagement()
                .invalidSessionUrl("/login") //session失效 跳转的地址
                .maximumSessions(1) //最大session数量 如果设置为1 表示一个用户只能有一个session（同一时间内只能有一个有效的登录）
                .maxSessionsPreventsLogin(true) //当前用户的session数量达到最大值后阻止用户的登录行为
                .expiredSessionStrategy(authenticationExpiredSessionStrategy); //会话过期策略（处理session并发问题）

    }

    /**
     * @desc 认证管理器
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true); //自动创建JdbcTokenRepositoryImpl中的表
        return tokenRepository;
    }

    /**
     * @desc 账号密码认证 provider
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        return daoAuthenticationProvider;
    }

    /**
     * @desc 手机登录
     * @return
     */
    @Bean
    public MobileAuthenticationFilter mobileAuthenticationFilter() throws Exception {
        MobileAuthenticationFilter authenticationFilter = new MobileAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManagerBean()); //设置认证管理器 用于将mobileToken与mobileProvider进行匹配
        authenticationFilter.setAuthenticationSuccessHandler(securityAuthenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(securityAuthenticationFailureHandler);
        mobileAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        return authenticationFilter;
    }

}
