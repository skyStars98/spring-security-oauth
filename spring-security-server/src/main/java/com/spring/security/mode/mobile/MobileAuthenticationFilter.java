package com.spring.security.mode.mobile;

import com.spring.security.constant.ModeConstant;
import com.spring.security.constant.RouteConstant;
import com.spring.security.constant.SessionConstant;
import com.spring.security.validate.entity.SmsCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author: daiguoqing
 * @Date: 2020-05-16
 * @Time: 17:45
 * @Version: spring-security-oauth 1.0
 */
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";

    private String mobileParameter = "mobile";
    private String smsCodeParameter = "smsCode";
    private boolean postOnly = true; //是否只处理post请求

    private SessionStrategy session = new HttpSessionSessionStrategy();

    //当前过滤器处理的请求地址
    public MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher(RouteConstant.LOGIN_MOBILE, ModeConstant.POST));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String mobile = this.obtainMobile(request);
            String smsCode = this.obtainSmsCode(request);
            SmsCode codeInSession = (SmsCode) session.getAttribute(new ServletWebRequest(request), SessionConstant.SMSCODE_KEY);

            if (StringUtils.isBlank(mobile)) {
                throw new AuthenticationServiceException("手机号码不能为空");
            }
            if(Objects.isNull(codeInSession) || !StringUtils.equals(codeInSession.getCode(), smsCode)){
                throw new AuthenticationServiceException("验证码不正确");
            }
            if(codeInSession.isExpired()){
                throw new AuthenticationServiceException("验证码已失效");
            }

            mobile = mobile.trim();
            MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    @Nullable
    protected String obtainSmsCode(HttpServletRequest request) {
        return request.getParameter(this.smsCodeParameter);
    }

    //获取手机号码
    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(this.mobileParameter);
    }

    //将request详情（ip、sessionId）设置到MobileAuthenticationToken中
    protected void setDetails(HttpServletRequest request, MobileAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return this.mobileParameter;
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

}
