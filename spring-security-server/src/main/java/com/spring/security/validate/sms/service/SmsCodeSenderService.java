package com.spring.security.validate.sms.service;

/**
 * @Author: daiguoqing
 * @Date: 2020-05-16
 * @Time: 10:02
 * @Version: spring-security-oauth 1.0
 */
public interface SmsCodeSenderService {

    void sender(String mobile, String code);
}
