package com.spring.security.constant;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-11
 * @Time: 21:04
 * @Version: eoms-micro-service 1.0
 */
public interface AuthorizeConstant {

    /**
     * 授权码 code
     */
    String CODE = "code";

    /**
     * 认证信息
     */
    String PRINCIPAL = "principal";

    /**
     * 客户端id
     */
    String CLIENT_ID = "client_id";

    /**
     * 请求令牌方式
     */
    String GRANT_TYPE = "grant_type";

    /**
     * 记住我的cookie名称
     */
    String REMEMBER_ME = "guan_eoms";

    /**
     * 授权码响应地址
     */
    String REDIRECT_URI = "redirect_uri";

    /**
     * token
     */
    String ACCESS_TOKEN = "access_token";

    /**
     *
     */
    String TOKEN_SERVICE = "tokenService";

    /**
     * oauth2处理方式
     */
    String RESPONSE_TYPE = "response_type";

    /**
     * 请求token时 授权码的key
     */
    String AUTHORIZATION_CODE = "authorization_code";

    /**
     * security认证异常
     */
    String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";

}
