package com.southwind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @SpringBootApplication 启动类注解
 * @EnableFeignClients  启动类的Feign注解，加了才能使用Feign
 */
@EnableFeignClients
@SpringBootApplication
public class FeignApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplicaiton.class,args);
    }
}
