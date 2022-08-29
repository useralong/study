package com.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 *  7.一个静态同步方法sendSms，一个普通同步方法call，两个线程用同一个对象分别调用这两个方法；两个线程先打印 发短信 还是打电话？打印结果：先打印打电话，再发短信！
 *  （静态同步方法，和普通同步方法的锁是两个锁，不管是不是同一个对象调用）
 *  8.一个静态同步方法sendSms，一个普通同步方法call，两个对象  调用这两个方法；两个线程先打印 发短信 还是打电话？打印结果：先打印打电话，再发短信！
 *  （还是和第7点一样，哪怕用不同的对象调用同步方法，因为静态、非静态同步方法调用的锁不一样，两个同步方法分别调用不同的锁，执行速度快的先打印）
 */
public class Test4 {
    public static void main(String[] args) {
        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();
        //A线程发短信（先调用，不一定先执行；）
        new Thread(()->{
            phone1.sendSms();
        },"A").start();
        //休息1秒
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //B线程Hello
        new Thread(()->{
            phone2.call();
        },"B").start();
//        Phone4 phone = new Phone4();
//        //A线程发短信（先调用，不一定先执行；）
//        new Thread(()->{
//            phone.sendSms();
//        },"A").start();
//        //休息1秒
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        //B线程Hello
//        new Thread(()->{
//            phone.call();
//        },"B").start();
    }
}


class Phone4{

    // 静态方法同步方法 锁的是 Class 类模板
    public static synchronized void sendSms(){
        //休息1秒
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发短信
        System.out.println("发短信");
    }

    //普通同步方法 锁的是方法的调用者
    public synchronized void call(){
        //打电话
        System.out.println("打电话");
    }

}
