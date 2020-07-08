package com.spring.gateway.constant;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-11
 * @Time: 20:49
 * @Version: eoms-micro-service 1.0
 */
public interface BasicConstant {

    /**
     * 用户cookie（用户访问某资源的路径）
     */
    String USER_URI = "user_uri";

    /**
     * 用户信息
     */
    String SESSION_USERINFO = "userinfo";

    /**
     * token
     */
    String HEADER_ACCESS_TOKEN = "access_token";

    /**
     * 业务 用户
     */
    String BUSINESS_SYSUSERAUTHS = "sysUserAuths";

}
