package com.spring.security.controller;

import com.spring.security.constant.ModeConstant;
import com.spring.security.constant.RequestConstant;
import com.spring.security.constant.SessionConstant;
import com.spring.security.properties.Oauth2Properties;
import com.spring.security.validate.code.ImageCodeGenerator;
import com.spring.security.validate.entity.ImageCode;
import com.spring.security.validate.entity.SmsCode;
import com.spring.security.validate.sms.SmsCodeGenerator;
import com.spring.security.validate.sms.service.SmsCodeSenderService;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
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
    private Oauth2Properties oauth2Properties;

    @Resource
    private SmsCodeSenderService smsCodeSenderServiceImpl;

    private RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper();

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/login")
    public String loginAction(){
        return "login/loginPage";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
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
        sessionStrategy.setAttribute(new ServletWebRequest(request), SessionConstant.SESSION_KEY, new ImageCode(imageCode.getCode(), imageCode.getExpireTime()));
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

    @GetMapping("/login/callback")
    public void loginCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        String authorization = oauth2Properties.getClientId() + ":" + oauth2Properties.getClientSecret();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encode(authorization.getBytes(StandardCharsets.UTF_8)));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("redirect_uri", oauth2Properties.getCodeCallbackUrl());
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(oauth2Properties.getAccessTokenUrl(), HttpMethod.POST, entity, String.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            String body = responseEntity.getBody();
            Map map = objectMapper.readValue(body, Map.class);
            System.out.println(map);
        }

    }

}