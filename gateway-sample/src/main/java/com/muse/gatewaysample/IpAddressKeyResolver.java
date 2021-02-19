package com.muse.gatewaysample;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * KeyResolver接口主要用于设置限流请求的key。
 * 我们可以实现该接口来指定需要对当前请求中对哪些因素进行流量控制。
 */
@Service
public class IpAddressKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()); // 根据请求IP来限流
    }
}
