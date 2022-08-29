package com.southwind.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloHandler {

    /**
     * 通过@Value 获取配置文件的端口
     * 端口是通过Config配置中心 远程获取GitHub仓库中 对应配置文件里的配置
     * 再通过下面的index方法检查是否获取并配置成功！！！
     */
    @Value("${server.port}")
    private String port;

    @GetMapping("/index")
    public String index(){
        return this.port;
    }
}
