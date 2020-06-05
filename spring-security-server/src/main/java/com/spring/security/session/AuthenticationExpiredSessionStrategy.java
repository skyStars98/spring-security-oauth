package com.spring.security.session;

import org.springframework.http.MediaType;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-05
 * @Time: 14:44
 * @Version: spring-security-oauth 1.0
 */
@Component
public class AuthenticationExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    /**
     * @desc session过期处理
     *       同一个账号 当后面的用户登录后 前面的用户session会过期 session过期后 用户再次刷新页面会进入当前方法处理
     *       sessionInformationExpiredEvent：session过期事件
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write("并发登录！");
    }

}
