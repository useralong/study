package com.websocket.chat.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.chat.pojo.Message;
import com.websocket.chat.utils.MessageUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  @ServerEndpoint 注解，定义 Endpoint 处理具体的 Websocket 消息接口
 *  @Component 注入让spring管理
 *  并且使用 @ServerEndpoint 声明一下自己的 GetHttpSessionConfig 配置类，让两个类中的session能够互通（不然session不互通）
 */
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfig.class)
@Component
public class ChatEndpoint {

    /**
     * ConcurrentHashMap 线程安全的Map对象
     * 每个用户会创建一个 ChatEndpoint，所以使用下面的 Map 集合将所有用户创建的 ChatEndpoint 对象，
     * 以用户名为 key 存储起来
     */
    private static Map<String,ChatEndpoint> onLineUsers = new ConcurrentHashMap<>();

    /**
     * 声明 Session 对象 通过该对象可以发送消息给指定的用户
     */
    private Session session;

    /**
     * 声明一个 HttpSession 对象，我们在HttpSession中存储了用户名
     */
    private HttpSession httpSession;

    /**
     * 连接建立时被调用
     * @param session
     * @param config
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        //将局部的 session 对象赋值给成员 session 变量
        this.session = session;
        //通过 config 获取 GetHttpSessionConfig 存储的 httpsession 对象
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        //赋值给成员变量
        this.httpSession = httpSession;

        //从 httpSession 中获取登录成功的用户
        String username = (String) httpSession.getAttribute("user");

        //连接建立成功，需要记录一下，将建立成功的当前 ChatEndpoint 对象存储到容器中，这里用户名当key
        onLineUsers.put(username, this);

        //系统消息：将当前的在线用户的用户名推送给所有的客户端
        //消息格式：{“isSystem”:true,"fromName":null,"message":["李四","王五"]}
        //1、获取消息
        String message = MessageUtils.getMessage(true, null, getNames());
        //2、调用方法进行系统消息的推送
        broadcastAllUsers(message);

    }

    private void broadcastAllUsers(String message){

        try {
            //要将该消息推送所有的客户端
            Set<String> names = onLineUsers.keySet();
            for (String name: names) {
                //通过key名在容器中找到成功建立连接的用户所对应的 ChatEndpoint 对象
                ChatEndpoint chatEndpoint = onLineUsers.get(name);
                //使用 ChatEndpoint 对象获取 websocket 的 session
                //使用session 获取 getBasicRemote() 对象 然后发送消息
                chatEndpoint.session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> getNames(){
        //获取所有使用websocket成功创立连接的用户名
        return onLineUsers.keySet();
    }

    /**
     * 接收到客户端发送的数据时被调用
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message,Session session){
        //接收客户端发送的数据，将 message 转换成 message 对象
        try {
            ObjectMapper mapper = new ObjectMapper();
            //使用 ObjectMapper 的 readValue 方法 将一个json转换成我们自定义的实体类
            Message msg = mapper.readValue(message, Message.class);
            //获取数据要发送的用户
            String toName = msg.getToName();
            //获取消息数据
            String data = msg.getMessage();
            //获取当前登录的用户
            String username = (String) httpSession.getAttribute("user");
            //获取推送给指定用户消息格式的数据
            //服务端推送给某个用户的数据格式：{“isSystem”:false,"fromName":“张三”,"message":“你好”}
            //{“isSystem”:不是系统消息,"fromName":“发送消息的用户”,"message":“消息内容”}
            String resultMsg = MessageUtils.getMessage(false, username, data);
            //给对应的用户发送消息（将消息发送到对应用户的session中）
            onLineUsers.get(toName).session.getBasicRemote().sendText(resultMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭时被调用
     * @param session
     */
    @OnClose
    public void onClose(Session session){
        //从容器中删除指定的用户
        String username = (String) httpSession.getAttribute("user");
        onLineUsers.remove(username);
        //获取推送消息
        String message = MessageUtils.getMessage(true, null, getNames());
        broadcastAllUsers(message);
    }

}
