package com.spring.security.validate.filter;

import com.spring.security.constant.MethodConstant;
import com.spring.security.constant.RouteConstant;
import com.spring.security.validate.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: daiguoqing
 * @Date: 2020-05-14
 * @Time: 19:22
 * @Version: spring-security-oauth 1.0
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    //OncePerRequestFilter：保证在一次请求中只通过一次filter
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(StringUtils.equals(RouteConstant.LOGIN_AUTHENTICATION, httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), MethodConstant.POST)){

            try {
                //validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                e.printStackTrace();
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
