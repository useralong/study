package com.southwind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Ribbon的启动类只需要 @SpringBootApplication 启动注解即可
 */
@SpringBootApplication
public class RibbonApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(RibbonApplicaiton.class,args);
    }

    /**
     * 因为要使用RestTemplate调用服务提供者的服务接口，所以需要在启动类写一个方法将RestTemplate返回
     * @Bean  使用此注解，将返回的RestTemplate注入到当前的IOC容器中
     * @LoadBalanced 声明一个基于Ribbon的负载均衡 （通过@LoadBalanced注解给 注入到IOC容器的 RestTemplate 提供负载均衡）
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
