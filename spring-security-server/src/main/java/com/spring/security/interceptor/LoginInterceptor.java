package com.spring.security.interceptor;

import com.spring.security.constant.SessionConstant;
import com.spring.security.properties.Oauth2Properties;
import com.spring.security.utils.GuavaUtil;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
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

            Map<String, Object> params = new HashMap<>();
            params.put("client_id", oauth2Properties.getClientId());
            params.put("response_type", "code");
            params.put("redirect_uri", oauth2Properties.getCodeCallbackUrl());
            String url = String.format("%s%s", oauth2Properties.getOauthAuthorizeUrl(), GuavaUtil.mapToUrl(params));

            //判断是否为Ajax请求
            if(Objects.equals("XMLHttpRequest", request.getHeader("X-Requested-With"))){
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().write(objectMapper.writeValueAsString(String.format("请登录：%s", url)));
            }

            response.sendRedirect(url);

            return false;
        }
        return true;
    }
}
