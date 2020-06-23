package com.spring.security.converter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-15
 * @Time: 11:57
 * @Version: eoms-micro-service 1.0
 */
@Component
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    /**
     * @desc 自定义资源服务器check token后返回的用户信息
     * @param authentication
     * @return
     */
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("user_name", authentication.getPrincipal());

        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            map.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        return map;
    }
}
