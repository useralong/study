package com.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 8锁，就是关于锁的8个问题
 *  1.标准情况下，两个线程先打印 发短信 还是打电话？ 打印结果：先发短信，再打电话！
 *  （锁的存在导致的）
 *  2.SendSms方法延迟4秒，两个线程先打印 发短信 还是打电话？  打印结果：依然是 先发短信，再打电话！
 *  （锁的存在导致的）
 */
public class Test1 {
    public static void main(String[] args) {
        Phone phone = new Phone();

        //A线程发短信（先调用，不一定先执行；）
        new Thread(()->{
            phone.sendSms();
        },"A").start();
        //休息1秒
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //B线程打电话
        new Thread(()->{
            phone.call();
        },"B").start();
    }
}

class Phone{

    // synchronized 锁的对象是方法的调用者！
    // sendSms()和 call() 两个方法，用的是同一个锁（ Phone上的锁 ）同一把锁下，谁先拿到谁先执行

    public synchronized void sendSms(){
        //休息1秒
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发短信
        System.out.println("发短信");
    }

    public synchronized void call(){
        //打电话
        System.out.println("打电话");
    }
}
