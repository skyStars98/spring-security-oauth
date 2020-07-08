package com.spring.gateway.service;

import com.spring.gateway.constant.ServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-28
 * @Time: 17:29
 * @Version: eoms-micro-service 1.0
 */
@FeignClient(value = ServiceConstant.OAUTH_AUTHORIZATION_SERVICE)
public interface TokenService {

    @GetMapping("/oauth/check_token")
    ResponseEntity<Map> checkToken(@RequestParam("token") String token);
}
