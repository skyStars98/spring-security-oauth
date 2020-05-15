package com.spring.security.validate.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/15
 * @Time: 10:22 下午
 * @Version: spring-security-oauth 1.0
 */
@Data
@NoArgsConstructor
public class SmsCode {

    /**
     * code验证码
     */
    private String code;

    /**
     * 过期时间 单位：秒
     */
    private LocalDateTime expireTime;

    /**
     * 构造函数
     * @param code
     * @param expireIn
     */
    public SmsCode(String code, Integer expireIn){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public Boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }
}
