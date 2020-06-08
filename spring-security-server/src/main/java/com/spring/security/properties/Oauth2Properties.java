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

    private String clientId;

    private String clientSecret;

    private String tokenExpireSecond;
}
