package com.websocket.chat.ws;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * 继承 ServerEndpointConfig.Configurator 内部类
 * 重写 modifyHandshake 方法
 * 通过 HandshakeRequest 获取 HttpSession
 * 再将 HttpSession 通过 ServerEndpointConfig 的 getUserProperties() 存起来
 * 【ChatEndpoint 类的 onOpen 方法就可以通过 EndpointConfig 将这个 HttpSession 取出来了】
 */
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        //将httpSession对象存储到配置对象中，key 为 HttpSession 的字节码名称
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
