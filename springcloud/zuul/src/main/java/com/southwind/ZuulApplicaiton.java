package com.southwind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 注解说明：
 * @EnableZuulProxy 注解包含了@EnableZuulServer  设置该类是网关的启动类
 * @EnableAutoConfiguration 可以帮助Spring Boot 应用将所有符合条件的 @Configuration 配置加载到当前 Spring Boot 创建并使用的IOC容器中
 */
@EnableZuulProxy
@EnableAutoConfiguration
public class ZuulApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplicaiton.class,args);
    }
}
