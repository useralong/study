package com.it.config;

import com.it.filters.AuthGlobalFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Collection;

//@Configuration
@Slf4j
public class GatewayRoutesConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        log.info("RouteLocator.....");
        return builder.routes()
                .route(r -> r.path("/XX/**") //设置路由断言
                        .filters(f -> f.stripPrefix(1) //设置服务降级
                                .filters((Collection<GatewayFilter>) new AuthGlobalFilter()))   //设置自己定义的过滤器
                        .uri("")   //设置请求的服务
                ).build();

//        return builder.routes()
//                .route(r -> r.host("localhost:8080").and().path("/api/**")
//                        .filters(f -> { f.addRequestHeader("X-TestHeader","foobar");
//                                return f.redirect(String.valueOf(HttpStatus.UNAUTHORIZED),"http:ip+端口/login");
//                        }).uri("http://localhost:8080").id("test_id")
//                )
//                .route(r -> r.path("/api/**")
//                        .filters(f -> f.addRequestHeader("X-TestHeader","foobar"))
//                        .uri("http://localhost:8080").id("test_id2")
//                ).build();

    }
}
