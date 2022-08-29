package com.it.filters;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义局部过滤器
 */
@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    /**
     * 构造函数(固定格式)
     */
    public LogGatewayFilterFactory(){
        super(LogGatewayFilterFactory.Config.class);
    }

    /**
     * 过滤器的逻辑处理
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(LogGatewayFilterFactory.Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if (config.isCacheLog()) {
                    System.out.println("cacheLog已经开启了....");
                }
                if (config.isConsoleLog()) {
                    System.out.println("consoleLog已经开启了....");
                }
                return chain.filter(exchange);
            }
        };
    }

    /**
     * 读取配置文件中的参数，赋值到 配置类中
     * 参数的顺序，对应application.yml文件中配置的Log自定义过滤器参数的顺序！！！
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("consoleLog", "cacheLog");
    }

    /**
     * 配置类
     *  @NoArgsConstructor  无参构造
     */
    @Data
    @NoArgsConstructor
    public static class Config{
        private boolean consoleLog;
        private boolean cacheLog;
    }
}
