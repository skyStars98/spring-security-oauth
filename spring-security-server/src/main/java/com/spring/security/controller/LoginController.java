package com.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/3
 * @Time: 7:02 下午
 * @Version: spring-security-oauth 1.0
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginAction(){
        return "login/loginPage";
    }
}
