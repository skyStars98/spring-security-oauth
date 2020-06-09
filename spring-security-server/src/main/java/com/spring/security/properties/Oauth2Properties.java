package com.spring.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-08
 * @Time: 18:12
 * @Version: spring-security-oauth 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = Oauth2Properties.OAUTH2)
public class Oauth2Properties {

    public static final String OAUTH2 = "oauth2";

    /**
     * 客户端标识
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * token过期时间 单位：秒
     */
    private String tokenExpireSecond;

    /**
     * 请求授权地址
     */
    private String oauthAuthorizeUrl;

    /**
     * 授权码回调地址
     */
    private String codeCallbackUrl;

    /**
     * 请求token地址
     */
    private String accessTokenUrl;

}
