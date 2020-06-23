package com.spring.security.constant;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-12
 * @Time: 8:38
 * @Version: eoms-micro-service 1.0
 */
public interface HttpConstant {

    /**
     * 首页
     */
    String HOME_PAGE = "/";

    /**
     * cookie的path
     */
    String COOKIE_PATH = "/";

    /**
     * 请求方式
     */
    String REQUEST_METHOD = "POST";

    /**
     * 前台服务域名
     */
    String DOMAIN = "eoms.guanph.gov.cn";

    /**
     * 登录失败路由
     */
    String LOGIN_ERROR_URL = "/login?error";

    /**
     * ajax请求方式
     */
    String XML_HTTP_REQUEST = "XMLHttpRequest";

    /**
     * ajax的请求头
     */
    String X_REQUESTED_WITH = "X-Requested-With";

    /**
     * 注销路由
     */
    String LOGOUT = "http://i.guanph.gov.cn/signout";

    /**
     * 注销成功后跳转的路由
     */
    String INTERACTIVE_VIEW = "http://eoms.guanph.gov.cn";

}
