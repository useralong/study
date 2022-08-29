package com.it.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class JsonUtil {

    public static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    /**
     * 通过ObjectMapper将对象转成json字符串
     * @param obj
     * @return
     * @throws Exception
     */
    public static String objectToJsonStr(Object obj)throws Exception{
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * json字符串转对象
     * @param content
     * @param valueType
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T jsonStrFromObject(String content,Class<T> valueType)throws Exception{
        T obj = objectMapper.readValue(content,valueType);
        return obj;
    }

    /**
     * 将Object对象转byte数组
     * @param obj
     * @return
     */
    public static byte[] toByteArray(Object obj){
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream stream = new ObjectOutputStream(bos);
            stream.writeObject(obj);
            stream.flush();
            bytes = bos.toByteArray();
            stream.close();
            bos.close();
        }catch (IOException io){
            io.printStackTrace();
        }
        return bytes;
    }
}
