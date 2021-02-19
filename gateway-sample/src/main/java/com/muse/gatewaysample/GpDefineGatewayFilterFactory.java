package com.muse.gatewaysample;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

/**
 * 自定义GatewayFilter
 *
 * 【注意】
 * 类名必须要统一以GatewayFilterFactory结尾，因为默认情况下过滤器的name会采用该自定义类的前缀，这里的name=GpDefine
 */
@Service
public class GpDefineGatewayFilterFactory extends AbstractGatewayFilterFactory<GpDefineGatewayFilterFactory.GpConfig> {

    public GpDefineGatewayFilterFactory() {
        super(GpConfig.class);
    }

    @Override
    public GatewayFilter apply(GpConfig config) {
        return ((exchange, chain) -> {
            System.out.println("GpDefineGatewayFilterFactory [Pre] Filter Request, config.getName() = " + config.getName());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> { // 在then方法中是请求执行结束之后的后置处理
                System.out.println("GpDefineGatewayFilterFactory [Post] Response Filter");
            }));
        });
    }



    /**
     * GpConfig只是一个配置类，该类中只有一个属性name。这个属性可以在yml文件中使用
     */
    public static class GpConfig {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}


