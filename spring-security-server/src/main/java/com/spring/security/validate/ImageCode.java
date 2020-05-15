package com.spring.security.validate;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Author: daiguoqing
 * @Date: 2020-05-14
 * @Time: 14:44
 * @Version: spring-security-oauth 1.0
 */
@Data
@NoArgsConstructor
public class ImageCode {

    /**
     * 验证码图片
     */
    private BufferedImage image;

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
     * @param image
     * @param code
     * @param expireIn
     */
    public ImageCode(BufferedImage image, String code, Integer expireIn){
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public Boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }
}
