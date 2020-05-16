package com.spring.security.validate.sms.service.impl;

import com.spring.security.validate.sms.service.SmsCodeSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: daiguoqing
 * @Date: 2020-05-16
 * @Time: 10:04
 * @Version: spring-security-oauth 1.0
 */
@Slf4j
@Service
public class SmsCodeSenderServiceImpl implements SmsCodeSenderService {

    @Override
    public void sender(String mobile, String code) {
        log.info("向手机{}发送短信验证码{}", mobile, code);
    }
}
