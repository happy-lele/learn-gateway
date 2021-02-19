//package com.muse.gatewaysentineldemo;
//
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.web.reactive.result.view.ViewResolver;
//
//import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
//import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
//import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
//import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
//import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
//
///**
// * 自定义API分组限流
// */
//@Configuration
//public class GatewayConfiguration1 {
//
//    private final List<ViewResolver> viewResolvers;
//
//    private final ServerCodecConfigurer serverCodecConfigurer;
//
//    public GatewayConfiguration1(ObjectProvider<List<ViewResolver>> viewResolvers,
//                                 ServerCodecConfigurer serverCodecConfigurer) {
//        this.viewResolvers = viewResolvers.getIfAvailable(Collections::emptyList);
//        this.serverCodecConfigurer = serverCodecConfigurer;
//    }
//
//    // 注入SentinelGatewayFilter
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public GlobalFilter sentinelGatewayFilter() {
//        return new SentinelGatewayFilter();
//    }
//
//    // 注入限流异常处理器
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
//        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
//    }
//
//    // 初始化限流规则
//    @PostConstruct
//    public void doInit() {
//        initCustomizedApis();
//        initGatewayRules();
//    }
//
//    /**
//     * 自定义API分组限流，将/foo/**和/baz/**进行统一分组，并提供name=first_customized_api，然后在初始化网关限流规则时，针对该name设置
//     * 限流规则。同时，我们可以通过setMatchStrategy来设置不同path下的限流参数策略
//     */
//    private void initCustomizedApis() {
//        Set<ApiDefinition> definitions = new HashSet<>();
//        ApiDefinition apiDefinition = new ApiDefinition("first_customized_api");
//        apiDefinition.setPredicateItems(new HashSet<ApiPredicateItem>() {
//            {
//                add(new ApiPathPredicateItem().setPattern("/foo/**")
//                        .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//                add(new ApiPathPredicateItem().setPattern("/baz/**")
//                        .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//            }
//        });
//        definitions.add(apiDefinition);
//        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
//    }
//
//    /**
//     * 针对分组name来设置限流规则
//     */
//    private void initGatewayRules() {
//        GatewayFlowRule rule = new GatewayFlowRule("first_customized_api")
//                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME).setCount(1)
//                .setIntervalSec(1);
//        Set<GatewayFlowRule> rules = new HashSet<>();
//        rules.add(rule);
//        GatewayRuleManager.loadRules(rules);
//    }
//}
