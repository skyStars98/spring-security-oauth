package com.spring.security.service;

import com.spring.security.entity.SysUser;
import com.spring.security.entity.SysUserAuths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/3
 * @Time: 3:02 下午
 * @Version: spring-security-oauth 1.0
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

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

        SysUserAuths user = new SysUserAuths();
        user.setUsername(username);
        user.setPassword("{noop}123456");
        user.setRole("Admin");

        SysUser sysUser = new SysUser(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole()), user);
        sysUser.getSysUserAuths().setPassword("");

        return sysUser;
    }
}
