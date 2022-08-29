package com.southwind.controller;

import com.southwind.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@RestController
@RequestMapping("/ribbon")
public class RibbonHandler {

    /**
     * 将IOC容器的RestTemplate 注入到 controller 中
     */
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/findAll")
    public Collection<Student> findAll(){
        /**
         * 因为调用的服务提供者已经在注册中心注册过了，注册到注册中心的名称是：provider
         * 所以服务提供者注册的名称：provider 可以直接替换 服务提供者的网络地址；使用名称：provider替换掉 ip + 端口
         * 如下操作：
         * http://localhost:8010/student/findAll  通过服务名称替换成  http://provider/student/findAll  路径
         */
        return restTemplate.getForObject("http://provider/student/findAll",Collection.class);
    }

    @GetMapping("/index")
    public String index(){
        return restTemplate.getForObject("http://provider/student/index",String.class);
    }

}
