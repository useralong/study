package com.websocket.chat.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.chat.pojo.ResultMessage;

/**
 * 封装消息的工具类
 */
public class MessageUtils {

    public static String getMessage(boolean isSystemMessage,String fromName,Object message){
        try {
            ResultMessage result = new ResultMessage();
            result.setIsSystem(isSystemMessage);
            result.setMessage(message);
            if(fromName != null){
                result.setFromName(fromName);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result); //使用ObjectMapper的writeValueAsString方法将对象转换成json字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
