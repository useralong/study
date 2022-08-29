package com.juc.volatile_test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 不保证原子性
 */
public class VDemo02 {

    //volatile 不保证原子性
    //原子类的 Integer （自己测试的时候发现 用了原子类，volatile 有没有，都没有影响最终结果；有待考证？）
    private volatile static AtomicInteger num = new AtomicInteger(); //使用原子类方式

    public static void add(){
        //num++; //不是一个原子性操作
        num.getAndIncrement(); //getAndIncrement() 是 AtomicInteger 的 +1 方法，用了底层的 CAS
    }

    public static void main(String[] args) {
        //循环 开启 20 个线程，每个线程执行 1000次 add() 方法
        //理论最终结果是 20000
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }
        //判断存活线程数量大于 2，就代表线程还没执行完
        //因为 Java 线程中 main线程 和 GC 线程是默认执行的
        while(Thread.activeCount() > 2){
            Thread.yield(); //执行yield，让所有线程继续竞争
        }
        System.out.println(Thread.currentThread().getName()+" "+num);
    }
}
