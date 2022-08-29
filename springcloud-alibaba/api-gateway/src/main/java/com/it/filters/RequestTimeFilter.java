package com.it.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
public class RequestTimeFilter implements GatewayFilter, Ordered {

    private static final Log log = LogFactory.getLog(GatewayFilter.class);
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //记录当前请求时的时间，通过exchange.getAttributes().put()存储这个时间记录
        exchange.getAttributes().put(REQUEST_TIME_BEGIN,System.currentTimeMillis());

        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    //exchange.getAttribute(REQUEST_TIME_BEGIN) 调用之前记录的时间
                    Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                    if(startTime != null){
                        System.out.println(exchange.getRequest().getURI().getRawPath() + "[耗时:" + (System.currentTimeMillis() - startTime) +"ms]");
                        log.info(exchange.getRequest().getURI().getRawPath() + "[耗时:" + (System.currentTimeMillis() - startTime) +"ms]");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
