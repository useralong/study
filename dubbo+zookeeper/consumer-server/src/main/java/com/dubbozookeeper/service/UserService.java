package com.dubbozookeeper.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service   // 消费者这里的 Service 注解是 Spring的，要注入 Spring 到容器中
public class UserService {
    //想拿到 provider-server 的票，要去注册中心拿到服务
    @Reference //引用 pom 坐标，可以定义与服务提供者：路径相同的接口名（直接把服务提供者对应接口拿过来即可）
    TicketService ticketService;

    public void buyTIcket(){
        String ticket = ticketService.getTicket();
        System.out.println(ticket);
    }

}
