package com.juc.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

//并发下，在集合里都会出现这个错：java.util.ConcurrentModificationException 并发修改异常
public class ListTest {
    public static void main(String[] args) {
//        List<String> list = Arrays.asList("1", "2", "3");
//        list.forEach(System.out::println);

        //并发下 ArrayList 集合是不安全的：List<String> list = new ArrayList<>();
        //synchronized
        /**
         * 解决方案：
         *  1、使用 List 下的 Vector 线程安全集合解决：List<String> list = new Vector<>();
         *  2、集合工具类 Collections 中有提供线程安全的同步集合，如： synchronizedList、synchronizedSet、synchronizedMap 等集合
         *  List<String> list = Collections.synchronizedList(new ArrayList<>());
         *  3、java.util.concurrent 类下有支持并发的可复制 List 集合（CopyOnWriteArrayList）List<String> list = new CopyOnWriteArrayList<>();
         */
        // CopyOnWrite 写入时复制， COW 计算机程序设计领域的一种优化策略
        // 多个线程调用的时候，list是唯一的，读取的时候是固定的，写入的时候存在覆盖操作
        // 在写入的时候避免覆盖，造成数据问题！（写入的时候复制，调用者写完后再放回去）

        //读写分离

        //CopyOnWriteArrayList 比 Vector 牛逼在哪里？
        //只要有 synchronized 的方法效率就很低，如：Vector 集合里面的 add 方法就用了 synchronized
        //如 CopyOnWriteArrayList 里面的 add 方法用的是 lock 锁

        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,5)); //list添加UUID，UUID截取5个字符串
                System.out.println(list);
            },String.valueOf(i)).start();

        }
    }
}
