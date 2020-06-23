package com.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-06
 * @Time: 16:20
 * @Version: spring-security-oauth 1.0
 */
@Configuration
@EnableAuthorizationServer //授权服务器
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManagerBean; //认证管理器

    @Resource
    private UserDetailsService userDetailsServiceImpl; //用户信息

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private DataSource dataSource;

    @Resource
    private UserAuthenticationConverter customUserAuthenticationConverter;

    /**
     * @desc 资源服务器访问端点控制
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()") //开放此端点 用于JWT校验
                .checkTokenAccess("permitAll()"); //开放此端点 用于资源服务器校验token
    }

    /**
     * @desc 配置客户端信息（基于数据库）
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    /**
     * @desc 令牌访问端点（生成令牌）
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenServices(tokenService())
                .authenticationManager(authenticationManagerBean)
                .userDetailsService(userDetailsServiceImpl)
                .accessTokenConverter(customAccessTokenConverter()); //设置令牌转换器
    }

    /**
     * @desc Token存储策略
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * @desc 生成Token的核心类 （能设置token的失效时间等）
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenService(){
        DefaultTokenServices tokenServices = new DefaultTokenServices(); //token生成类
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true); //是否产生刷新令牌
        tokenServices.setAccessTokenValiditySeconds(60 * 30);
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        return tokenServices;
    }

    /**
     * @desc 令牌转换器 （将身份认证数据存储在令牌内部）
     * @return
     */
    @Bean
    public AccessTokenConverter customAccessTokenConverter(){
        DefaultAccessTokenConverter converter = new DefaultAccessTokenConverter(); //默认的令牌转换器
        converter.setUserTokenConverter(customUserAuthenticationConverter); //用户认证信息转换器 （使资源服务器check token的时候返回你定义的用户信息）
        return converter;
    }

}
