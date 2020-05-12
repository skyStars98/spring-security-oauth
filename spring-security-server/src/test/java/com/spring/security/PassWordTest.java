package com.spring.security;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/12
 * @Time: 14:25
 * @Version: spring-security-oauth 1.0
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PassWordTest {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void passTest(){
        String pwd = "dai123";
        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(pwd));
    }
}
