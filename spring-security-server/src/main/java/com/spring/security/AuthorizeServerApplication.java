package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/2
 * @Time: 4:05 下午
 * @Version: spring-security-oauth 1.0
 */
@SpringBootApplication
@EnableEurekaClient
public class AuthorizeServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizeServerApplication.class, args);
    }
}
