package com.spring.security.mode.mobile;

import com.spring.security.abstraction.AbstractAuthenticationProvider;
import lombok.Data;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: daiguoqing
 * @Date: 2020-05-16
 * @Time: 19:08
 * @Version: spring-security-oauth 1.0
 */
@Data
@Component
public class MobileAuthenticationProvider extends AbstractAuthenticationProvider {

    private UserDetailsService userDetailsService;

    //进行身份认证
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;

        UserDetails userDetails = userDetailsService.loadUserByUsername(mobileAuthenticationToken.getPrincipal().toString());

        if(Objects.isNull(userDetails)){
            throw new InternalAuthenticationServiceException("用户不存在");
        }

        //认证成功后重新构造MobileAuthenticationToken将用户信息放入
        //这里调用的是MobileAuthenticationToken认证成功的构造函数
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails()); //将用户的请求信息重新放入到认证成功的MobileAuthenticationToken中

        return authenticationToken;
    }

    //AuthenticationManager通过此方法挑选Provider来处理 xxxxToken
    //Provider：MobileAuthenticationProvider    xxxToken：MobileAuthenticationToken
    //以上两个都是我们自定义的 用来处理手机短信验证登录
    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication); //判断传进来的authentication是不是MobileAuthenticationToken这种类型的
    }
}
