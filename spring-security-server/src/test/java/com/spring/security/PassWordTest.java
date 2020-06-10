package com.spring.security;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.List;

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
