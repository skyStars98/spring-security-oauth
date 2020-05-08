package com.spring.security.server.browser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/3
 * @Time: 3:02 下午
 * @Version: spring-security-oauth 1.0
 */
@Component
@Slf4j
public class DataBaseUserDetailsService implements UserDetailsService {

    /**
     * @desc disabled：账号是否可用 accountExpired：账号是否过期
     *       credentialsExpired：密码是否过期 accountLocked：账号是否被锁定
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录用户名：{}", username);
        return User.withUsername(username).password("{noop}123456").authorities("Admin")
                .disabled(false).accountExpired(false).credentialsExpired(false).accountLocked(false).build();
    }
}
