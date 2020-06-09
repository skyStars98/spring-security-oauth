package com.spring.security.mvc;

import com.google.common.collect.Lists;
import com.spring.security.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-09
 * @Time: 16:43
 * @Version: spring-security-oauth 1.0
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> exclude = Lists.newArrayList(
                "/login",
                "/oauth/authorize",
                "/login/callback",
                "/login/verification/code",
                "/login/sms/code"
        );
        registry.addInterceptor(loginInterceptor).excludePathPatterns(exclude);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }
}
