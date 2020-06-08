package com.spring.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/3
 * @Time: 10:15 下午
 * @Version: spring-security-oauth 1.0
 */
@Slf4j
@Component
public class SecurityAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * AuthenticationSuccessHandler接口：登陆成功后进行处理的处理器
     * SavedRequestAwareAuthenticationSuccessHandler是上面接口的实现类，记录了上一次请求的路径
     * RequestCache：提供了缓存客户端请求的能力 通过HttpSessionRequestCache获取
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("登陆成功");
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        log.info("跳转地址：{}", savedRequest.getRedirectUrl());
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
