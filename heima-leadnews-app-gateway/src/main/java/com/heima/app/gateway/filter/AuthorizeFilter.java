package com.heima.app.gateway.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heima.app.gateway.utils.AppJwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: tang
 * @date: Create in 21:42 2021/8/29
 * @description: 全局用户认证过滤器
 */
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();
        ServerHttpResponse resp = exchange.getResponse();
        //若是登陆页面 则旅行
        if(StringUtils.contains(req.getURI().getPath(), "/login/login_auth")){
            return chain.filter(exchange);
        }
        //获取用户的token信息
        String token = req.getHeaders().getFirst("token");
        if(StrUtil.isEmpty(token)){
            //token不存在
            resp.setStatusCode(HttpStatus.UNAUTHORIZED);
            return resp.setComplete();
        }
        //解析token
        try {
            Claims claims = AppJwtUtil.getClaimsBody(token);
            if(ObjectUtil.isEmpty(claims)){
                resp.setStatusCode(HttpStatus.UNAUTHORIZED);
                return resp.setComplete();
            }
            int res = AppJwtUtil.verifyToken(claims);
            if(res == -1 || res == 0){
                //token有效或合法 获取id信息
                Integer id = (Integer) claims.get("id");
                log.info("find id{},from url{}",id,req.getURI().getPath());
                //重新设置id信息到请求头中
                ServerHttpRequest serverHttpRequest = req.mutate().headers(httpHeaders -> {
                    httpHeaders.add("userId", id + "");
                }).build();
                //添加到请求中
                exchange.mutate().request(serverHttpRequest).build();
            }
        } catch (Exception e) {
            log.error("token解析失败；",e);
            //给用户返回未认证信息
            resp.setStatusCode(HttpStatus.UNAUTHORIZED);
            return resp.setComplete();
        }
        //放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
