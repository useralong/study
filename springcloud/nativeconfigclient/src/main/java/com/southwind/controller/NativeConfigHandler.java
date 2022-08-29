package com.southwind.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/native")
public class NativeConfigHandler {

    /**
     * 通过@Value注解去取配置文件的端口
     * 且此配置所在的配置文件是Config配置中心的配置文件，经过nativeconfigclient的配置后调用获取的
     */
    @Value("${server.port}")
    private String port;
    /**
     * 通过@Value注解去取配置文件的foo
     * 且此配置所在的配置文件是Config配置中心的配置文件，经过nativeconfigclient的配置后调用获取的
     */
    @Value("${foo}")
    private String foo;

    /**
     * 写一个index方法，调用controller中声明的：port、foo 两个变量
     * 启动服务查看两个变量是否通过nativeconfigclient的配置后  成功获取 Config配置中心里对应的配置文件中的配置
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return this.port+"-"+this.foo;
    }


}
