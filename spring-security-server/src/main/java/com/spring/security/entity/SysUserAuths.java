package com.spring.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-23
 * @Time: 12:42
 * @Version: spring-security-oauth 1.0
 */
@Data
public class SysUserAuths implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 登录名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户角色
     */
    private String role;

}
