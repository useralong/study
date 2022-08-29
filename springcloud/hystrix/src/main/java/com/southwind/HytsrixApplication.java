package com.southwind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @SpringBootApplication 启动类注解
 * @EnableFeignClients  动类的Feign注解，加了才能使用Feign
 * @EnableCircuitBreaker    声明启用数据监控注解
 * @EnableHystrixDashboard  声明启用可视化数据监控注解
 */
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrixDashboard
public class HytsrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(HytsrixApplication.class,args);
    }
}
