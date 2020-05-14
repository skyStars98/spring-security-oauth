package com.spring.security.validate.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @Author: daiguoqing
 * @Date: 2020-05-14
 * @Time: 20:09
 * @Version: spring-security-oauth 1.0
 */
public class ValidateCodeException extends AuthenticationException {

    //AuthenticationException：所有身份认证过程中异常的父类
    public ValidateCodeException(String msg) {
        super(msg);
    }

}
