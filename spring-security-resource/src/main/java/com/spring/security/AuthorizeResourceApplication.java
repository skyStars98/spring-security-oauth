package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-06
 * @Time: 17:51
 * @Version: spring-security-oauth 1.0
 */
@EnableEurekaClient
@SpringBootApplication
public class AuthorizeResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizeResourceApplication.class, args);
    }
}
