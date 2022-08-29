package com.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 *  5.增加两个静态的同步方法(sendSms() call()方法加上 static)只有一个对象，两个线程先打印 发短信 还是打电话？打印结果：先打印发短信，再打电话！
 *  （静态同步方法锁的是calss，两个方法静态同步方法在一个calss中，还是一个锁）
 *  6.两个对象，增加两个静态的同步方法(sendSms() call()方法加上 static)两个线程先打印 发短信 还是打电话？打印结果：先打印发短信，再打电话！
 *  (因为两个对象是同一个class类模板)
 */
public class Test3 {
    public static void main(String[] args) {
        //两个对象的calss类模板只有一个(是同一个class  new了两个对象)
        Phone3 phone1 = new Phone3();
        Phone3 phone2 = new Phone3();
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
//        Phone3 phone = new Phone3();
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

// Phone3 只有唯一的一个class 对象（一个类只能有一个class对象）
class Phone3{

    // synchronized 锁的对象是方法的调用者！
    // static 静态方法
    // 类一加载就有了！是一个Class 模板，所以锁的是class
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

    public static synchronized void call(){
        //打电话
        System.out.println("打电话");
    }

}
