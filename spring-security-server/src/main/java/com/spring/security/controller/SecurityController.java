package com.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/2
 * @Time: 6:00 下午
 * @Version: spring-security-oauth 1.0
 */
@RestController
public class SecurityController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
