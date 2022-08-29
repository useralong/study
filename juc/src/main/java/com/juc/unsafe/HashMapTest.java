package com.juc.unsafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 同理可证 并发下，map集合里也会出现这个错 java.util.ConcurrentModificationException 并发修改异常
 * 解决方案：
 *  1、集合工具类 Collections 中有提供线程安全的同步集合，如 Collections.synchronizedMap()：Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
 *  2、java.util.concurrent 类下有支持并发的 HashMap 集合(ConcurrentHashMap)：Map<String, Object> map = new ConcurrentHashMap<>();
 *
 */
public class HashMapTest {
    public static void main(String[] args) {
        //HashMap() 默认的hashMap
        //HashMap(int *) hashMap初始化容量
        //HashMap(int *,float *) hashMap初始化容量，跟了一个加载因子

        // map 是这样用的吗？ 不是，工作中不用 HashMap
        // 默认等价于什么？ new HashMap<>(16,0.75)    (初始化容量默认为：16，加载因子默认为：0.75)
        //Map<String, Object> map = new HashMap<>();
        //Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, Object> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,5)); //map 添加UUID，UUID截取5个字符串
                System.out.println(map);
            },String.valueOf(i)).start();
        }


    }
}
