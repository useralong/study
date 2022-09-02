package com.dubbozookeeper.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

//zookeeper：服务注册与发现

@Service   // 使用dubbo的@Service注解,可以被扫描到，在项目启动就自动注册到注册中心
@Component // 使用 @Component 注解放在容器中，使用了Dubbo后尽量不要用 service 注解
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "hello allen";
    }
}
