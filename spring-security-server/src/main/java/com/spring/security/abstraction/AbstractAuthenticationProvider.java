package com.spring.security.abstraction;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author: daiguoqing
 * @Date: 2020-05-17
 * @Time: 18:17
 * @Version: spring-security-oauth 1.0
 */
public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {

    @Override
    public abstract Authentication authenticate(Authentication authentication) throws AuthenticationException;

    @Override
    public abstract boolean supports(Class<?> aClass);
}
