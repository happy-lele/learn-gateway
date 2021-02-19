package com.muse.gatewaysentineldemo;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;

/**
 * 自定义异常
 */
@Configuration
public class GatewayConfiguration2 {

    private final List<ViewResolver> viewResolvers;

    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration2(ObjectProvider<List<ViewResolver>> viewResolvers,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolvers.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    // 注入一个全局限流过滤器SentinelGatewayFilter
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    // 注入限流异常处理器
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
//        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
//    }

    // 注入自定义的限流异常处理器
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GpSentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new GpSentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    // 初始化限流规则
    @PostConstruct
    public void doInit() {
        initGatewayRules();
    }

    /**
     * Route维度限流
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        GatewayFlowRule gatewayFlowRule = new GatewayFlowRule("gateway-nacos-provider").setCount(1).
                setIntervalSec(1);
        rules.add(gatewayFlowRule);
        GatewayRuleManager.loadRules(rules);
    }
}

