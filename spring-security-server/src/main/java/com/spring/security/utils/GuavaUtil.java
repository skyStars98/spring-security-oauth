package com.spring.security.utils;

import com.google.common.base.Joiner;

import java.util.Map;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-09
 * @Time: 17:57
 * @Version: spring-security-oauth 1.0
 */
public class GuavaUtil {

    public static String mapToUrl(Map<String, Object> map){
        String prefix = "?";
        return prefix + Joiner.on("&").withKeyValueSeparator("=").join(map);
    }
}
