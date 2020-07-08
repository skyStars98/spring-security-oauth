package com.spring.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.gateway.constant.AuthorizeConstant;
import com.spring.gateway.constant.BasicConstant;
import com.spring.gateway.constant.BusinessConstant;
import com.spring.gateway.constant.RedisConstant;
import com.spring.gateway.model.ResponseMessage;
import com.spring.gateway.model.SysUserAuths;
import com.spring.gateway.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-28
 * @Time: 17:00
 * @Version: eoms-micro-service 1.0
 */
@Component
public class GlobalGatewayFilter implements GlobalFilter, Ordered {

    @Value("${gateway.ignore.urls}")
    String[] ignoreUrls;

    @Resource
    private TokenService tokenService;

    private ObjectMapper mapper = new ObjectMapper();

    @Resource
    private RedisTemplate<String, List<String>> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpRequest request = exchange.getRequest();
            String url = request.getURI().getPath();

            for(String ignore : ignoreUrls){
                if(url.contains(ignore)){
                    return chain.filter(exchange);
                }
            }

            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(BasicConstant.HEADER_ACCESS_TOKEN);

            if(StringUtils.isNotBlank(token)){
                ResponseEntity<Map> responseEntity = tokenService.checkToken(token);

                if(responseEntity.getStatusCode().is2xxSuccessful()){
                    Map body = responseEntity.getBody();

                    Map principal = (Map) body.get(AuthorizeConstant.USER_NAME);
                    String data = mapper.writeValueAsString(principal.get(BasicConstant.BUSINESS_SYSUSERAUTHS));

                    SysUserAuths sysUserAuths = mapper.readValue(data, SysUserAuths.class);
                    Long postId = sysUserAuths.getPostId();

                    List<String> routes = redisTemplate.opsForValue().get(RedisConstant.USER_ROLE_RESOURCE + postId);

                    for(String route : routes){
                        if (url.contains(route)) {
                            return chain.filter(exchange);
                        }
                    }

                }else {
                    return this.responseMessage(exchange.getResponse(),
                            BusinessConstant.KEY_CANNOTRESOURCE.getCode(), false, BusinessConstant.KEY_CANNOTRESOURCE.getMsg());
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return this.responseMessage(exchange.getResponse(),
                BusinessConstant.KEY_UNAUTHORIZED.getCode(), false, BusinessConstant.KEY_UNAUTHORIZED.getMsg());
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> responseMessage(ServerHttpResponse response, int code, boolean status, String message){
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String writeMessage = "";

        try {
            writeMessage = mapper.writeValueAsString(new ResponseMessage<Object>(code, status, message, null));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        byte[] bytes = writeMessage.getBytes(StandardCharsets.UTF_8);
        DataBuffer wrap = response.bufferFactory().wrap(bytes);

        return response.writeWith(Flux.just(wrap));
    }
}
