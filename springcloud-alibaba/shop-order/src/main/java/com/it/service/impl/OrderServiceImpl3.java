package com.it.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 给message资源添加两个异常处理方法
 */
@Service
@Slf4j
public class OrderServiceImpl3{

    int i = 0;
    /**
     * @SentinelResource 注解
     * value 定义资源名称，也就是Sentinel控制台中的微服务的资源名称
     * 定义当资源内部发生异常时的处理逻辑
     * blockHandler 定义当资源内部发生了 BlockException异常时应该走的方法（捕获的是Sentinel定义的异常，如Sentinel控制台规则定义的异常）
     * fallback 定义当资源内部发生了 Throwable 异常应该进入的方法，也就是说只要是异常都会进入fallback方法
     *
     * value = "message" 定义资源名称
     * blockHandler = "blockHandler" 定义发生了 BlockException异常时走设置的 ="blockHandler"方法
     * fallback = "fallback" 定义发生了 Throwable异常时走设置的="fallback"方法（也就是所有异常都会走这个）
     * blockHandlerClass = ** 设置 blockHandler方法所在的类
     * fallbackClass = ** 设置 fallback方法所在的类
     * @return
     */
    @SentinelResource(
            value = "message",
            blockHandlerClass = OrderServiceImpl3BlockHandlerClass.class,
            blockHandler = "blockHandler",
            fallbackClass = OrderServiceImpl3FallbackClass.class,
            fallback = "fallback"
    )
    public String message(String name){
        i++;
        if (i % 3 == 0) {
            throw new RuntimeException();
        }
        System.out.println("message");
        return "message";
    }


}

