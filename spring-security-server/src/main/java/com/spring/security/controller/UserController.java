package com.spring.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/2
 * @Time: 6:00 下午
 * @Version: spring-security-oauth 1.0
 */
@Slf4j
@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    //获取请求的所有信息：用户信息、ip地址、sessionId等
    //Authentication跟SecurityContextHolder.getContext().getAuthentication()等价
    //写在方法参数上spring会自动帮我们去context中获取
    @GetMapping("/user/info")
    public Object getCurrentUser(Authentication authentication){
        if(Objects.nonNull(authentication)){
            return authentication;
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

    //获取用户信息
    //@AuthenticationPrincipal：只获取用户信息，会帮我们省略上面的ip地址等
    @GetMapping("/user/principal")
    public Object getUserDetails(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }

}
