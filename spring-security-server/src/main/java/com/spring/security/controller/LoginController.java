package com.spring.security.controller;

import com.spring.security.constant.*;
import com.spring.security.entity.SysUserAuths;
import com.spring.security.properties.Oauth2Properties;
import com.spring.security.properties.Oauth2Properties.Client;
import com.spring.security.validate.code.ImageCodeGenerator;
import com.spring.security.validate.entity.ImageCode;
import com.spring.security.validate.entity.SmsCode;
import com.spring.security.validate.sms.SmsCodeGenerator;
import com.spring.security.validate.sms.service.SmsCodeSenderService;
import com.sun.org.apache.xml.internal.security.utils.Base64;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

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

    private ObjectMapper mapper = new ObjectMapper();

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

    /**
     * @desc
     * @param request
     * @param response
     */
    @GetMapping("/oauth/callback")
    public void loginCallBack(HttpServletRequest request, HttpServletResponse response) {
        try {
            Client client = oauth2Properties.getClient();
            String code = request.getParameter(AuthorizeConstant.CODE);
            String authorize = client.getClientId() + ":" + client.getClientSecret();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encode(authorize.getBytes(StandardCharsets.UTF_8)));

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add(AuthorizeConstant.GRANT_TYPE, AuthorizeConstant.AUTHORIZATION_CODE);
            params.add(AuthorizeConstant.CODE, code);
            params.add(AuthorizeConstant.REDIRECT_URI, client.getOauthCallbackUrl());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(client.getAccessTokenUrl(), HttpMethod.POST, entity, String.class);

            if(responseEntity.getStatusCode().is2xxSuccessful()){
                String body = responseEntity.getBody();
                Map map = mapper.readValue(body, Map.class);
                String access_token = map.get(AuthorizeConstant.ACCESS_TOKEN).toString();
                this.loginIn(request, response, access_token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/signout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.removeAttribute(BasicConstant.SESSION_USERINFO);
        session.invalidate();
        response.sendRedirect(HttpConstant.LOGOUT);
    }

    private void loginIn(HttpServletRequest request, HttpServletResponse response, String accessToken) throws IOException {
        String resourceUrl = oauth2Properties.getResource().getOauthResourceUrl();

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(AuthorizeConstant.ACCESS_TOKEN, accessToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, String.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            String body = responseEntity.getBody();
            Map map = mapper.readValue(body, Map.class);

            Map principal = (Map) map.get(AuthorizeConstant.PRINCIPAL);
            String json = mapper.writeValueAsString(principal.get(BasicConstant.BUSINESS_SYSUSERAUTHS));
            SysUserAuths sysUserAuths = mapper.readValue(json, SysUserAuths.class);

            HttpSession session = request.getSession();
            session.setAttribute(BasicConstant.SESSION_USERINFO, sysUserAuths);

            Cookie[] cookies = request.getCookies();
            Optional<Cookie> cookie = Arrays.stream(cookies)
                    .filter(cookie1 -> cookie1.getName().equals(BasicConstant.USER_URI)).findFirst();

            if(cookie.isPresent()){
                Cookie cookie1 = cookie.get();
                cookie1.setMaxAge(0);
                cookie1.setDomain(HttpConstant.DOMAIN);
                cookie1.setPath(HttpConstant.COOKIE_PATH);
                response.addCookie(cookie1);
                response.sendRedirect(cookie.get().getValue());
                return;
            }

            response.sendRedirect(HttpConstant.HOME_PAGE);
        }
    }

}