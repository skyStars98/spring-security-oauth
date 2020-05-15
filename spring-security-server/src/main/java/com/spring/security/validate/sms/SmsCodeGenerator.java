package com.spring.security.validate.sms;

import com.spring.security.validate.entity.SmsCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/15
 * @Time: 11:27 下午
 * @Version: spring-security-oauth 1.0
 */
@Component
public class SmsCodeGenerator {

    public SmsCode randomCode(){
        /*String code = RandomStringUtils.randomNumeric();
        return new SmsCode(code, );*/
        return null;
    }
}
