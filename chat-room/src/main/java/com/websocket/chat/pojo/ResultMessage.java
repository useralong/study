package com.websocket.chat.pojo;

/**
 * 服务器发送给浏览器的websocket数据
 */
public class ResultMessage {
    private boolean isSystem;
    private String fromName;
    private Object message; // 如果是系统消息是数组

    public boolean isSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean system) {
        isSystem = system;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
