package com.spring.security.interceptor;

import com.spring.security.constant.*;
import com.spring.security.properties.Oauth2Properties;
import com.spring.security.properties.Oauth2Properties.Client;
import com.spring.security.utils.GuavaUtil;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-09
 * @Time: 16:08
 * @Version: spring-security-oauth 1.0
 */
@Slf4j
@Component
@EnableConfigurationProperties(Oauth2Properties.class)
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private Oauth2Properties oauth2Properties;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        Object user = session.getAttribute(SessionConstant.SESSION_USERINFO);

        if(Objects.isNull(user)){
            log.info("被拦截的路径：{}", request.getRequestURL());

            Client client = oauth2Properties.getClient();

            Map<String, Object> map = new HashMap<>();
            map.put(AuthorizeConstant.CLIENT_ID, client.getClientId());
            map.put(AuthorizeConstant.RESPONSE_TYPE, AuthorizeConstant.CODE);
            map.put(AuthorizeConstant.REDIRECT_URI, client.getOauthCallbackUrl());

            String url = String.format("%s%s", client.getOauthAuthorizeUrl(), GuavaUtil.mapToUrl(map));

            //判断是否为Ajax请求
            if(Objects.equals(HttpConstant.XML_HTTP_REQUEST, request.getHeader(HttpConstant.X_REQUESTED_WITH))){
                ResponseMessage message = ResponseMessage.failed("请登录", url);
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().write(objectMapper.writeValueAsString(message));
                return false;
            }

            Cookie cookie = new Cookie(BasicConstant.USER_URI, request.getRequestURI());
            cookie.setHttpOnly(true);
            cookie.setDomain(HttpConstant.DOMAIN);
            cookie.setPath(HttpConstant.COOKIE_PATH);
            response.addCookie(cookie);

            response.sendRedirect(url);

            return false;
        }
        return true;
    }
}
