package com.spring.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-12
 * @Time: 18:01
 * @Version: eoms-micro-service 1.0
 */
@RestController
public class AuthorizationController {

    /**
     * @desc 获取用户信息
     * @param principal 用户认证成功后的信息
     * @return
     */
    @PostMapping("/user/me")
    public Principal getUser(Principal principal){
        return principal;
    }

}
