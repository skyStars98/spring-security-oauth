package com.spring.security.controller;

import com.spring.security.constant.ModeConstant;
import com.spring.security.constant.RequestConstant;
import com.spring.security.constant.SessionConstant;
import com.spring.security.validate.code.ImageCodeGenerator;
import com.spring.security.validate.entity.ImageCode;
import com.spring.security.validate.entity.SmsCode;
import com.spring.security.validate.sms.SmsCodeGenerator;
import com.spring.security.validate.sms.service.SmsCodeSenderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @Author: daiguoqing
 * @Date: 2020/5/3
 * @Time: 7:02 下午
 * @Version: spring-security-oauth 1.0
 */
@Controller
public class LoginController {

    @Resource
    private ImageCodeGenerator imageCodeGenerator;

    @Resource
    private SmsCodeGenerator smsCodeGenerator;

    @Resource
    private SmsCodeSenderService smsCodeSenderServiceImpl;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/login")
    public String loginAction(){
        return "login/loginPage";
    }

    /**
     * @desc 获取验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/login/verification/code")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = imageCodeGenerator.createImageCode(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SessionConstant.SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), ModeConstant.JPEG, response.getOutputStream());
    }

    /**
     * @desc 短信验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/login/sms/code")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, RequestConstant.MOBILE);
        SmsCode smsCode = smsCodeGenerator.generate();
        sessionStrategy.setAttribute(new ServletWebRequest(request), SessionConstant.SMSCODE_KEY, smsCode);
        smsCodeSenderServiceImpl.sender(mobile, smsCode.getCode());
    }

}
