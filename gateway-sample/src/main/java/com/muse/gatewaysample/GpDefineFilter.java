package com.muse.gatewaysample;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Service
public class GpDefineFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("GpDefineFilter [pre]-Enter GpDefineFilter");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            System.out.println("GpDefineFilter [post]-return Result");
        }));
    }

    /**
     * 表示该过滤器的执行顺序，值越小，执行优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
