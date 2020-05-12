package com.spring.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/2
 * @Time: 6:00 下午
 * @Version: spring-security-oauth 1.0
 */
@RestController
@Slf4j
public class SecurityController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/errors")
    public void reload(HttpSession session){
        Object spring_security_last_exception = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        log.info("" + spring_security_last_exception);
    }
}
