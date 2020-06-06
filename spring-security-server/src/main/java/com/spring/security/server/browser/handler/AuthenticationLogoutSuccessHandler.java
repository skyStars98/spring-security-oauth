package com.spring.security.server.browser.handler;

import com.spring.security.constant.RouteConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-06
 * @Time: 11:24
 * @Version: spring-security-oauth 1.0
 */
@Slf4j
@Component
public class AuthenticationLogoutSuccessHandler implements LogoutSuccessHandler {

    //用户退出成功后的处理器
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("退出成功");
        httpServletResponse.sendRedirect(RouteConstant.LOGIN);
    }
}
