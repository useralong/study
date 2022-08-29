package com.juc.unsafe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 同理可证 并发下，set集合里也会出现这个错 java.util.ConcurrentModificationException 并发修改异常
 * 解决方案：
 *  1、集合工具类 Collections 中有提供线程安全的同步集合，如 Collections.synchronizedSet()：Set<Object> set = Collections.synchronizedSet(new HashSet<>());
 *  2、java.util.concurrent 类下有支持并发的可复制 Set 集合（CopyOnWriteArraySet）：Set<String> set = new CopyOnWriteArraySet<>();
 *  方案1是将set集合转成了synchronized，效率不高；方案2是通过写入复制，即保证了效率性能问题，也保证了安全
 */
public class SetTest {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        //Set<String> set = Collections.synchronizedSet(new HashSet<>());
        //Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,5)); //set添加UUID，UUID截取5个字符串
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }
}
