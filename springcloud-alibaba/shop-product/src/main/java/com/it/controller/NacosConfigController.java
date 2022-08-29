package com.it.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RefreshScope 注解：实现动态读取配置中心的配置
 * 只需要在需要动态读取配置的类上添加此注解就可以
 */
//@RestController
//@RefreshScope
public class NacosConfigController {

    /**
     * @Value("${config.appName}") 通过key读取配置文件的值
     * 但不是动态读取的，需要在类上加@RefreshScope注解
     */
    @Value("${config.appName}")
    private String appName;

    @Value("${config.env}")
    private String env;

    @GetMapping("/nacos-config-test2")
    public String nacosConfingTest2() {
        return appName;
    }

    @GetMapping("/nacos-config-test3")
    public String nacosConfingTest3() {
        return env;
    }
}
