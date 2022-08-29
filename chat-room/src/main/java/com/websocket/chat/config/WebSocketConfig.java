package com.websocket.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *  @Configuration 代表配置类
 *  Spring不会创建 Endpoint 对象，所以需要 WebSocketConfig 配置类对 ChatEndpoint 类的支持
 *  也就是将 ServerEndpointExporter 对象注入到 Bean 中去扫描并支持 ChatEndpoint 类的 @ServerEndpoint 注解
 */
@Configuration
public class WebSocketConfig {

    /**
     * 注入 ServerEndpointExporter 对象到 bean 里
     * @Bean 注解，代表自动注册使用相关的代码
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
