package com.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 *  3.在Phone中增加了一个普通方法打印 Hello,A线程调用同步方法 B线程调用普通方法；两个线程先打印 发短信 还是Hello？打印结果：先打印Hello，再发短信！
 *  （普通方法不受锁的影响）
 *  4.new两个Phone对象，phone1对应A线程 phone2对应B线程，都调用同步方法；两个线程先打印 发短信 还是打电话？打印结果：先打印打电话，再发短信！
 *  （两个对象，两个调用者，两把锁）
 */
public class Test2 {
    public static void main(String[] args) {
        //两个对象，两个调用者，有两把锁（按时间执行，所以谁没延迟谁执行的快）
        Phone2 phone1 = new Phone2();
        Phone2 phone2 = new Phone2();
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
        //B线程打电话
        new Thread(()->{
            phone2.call();
        },"B").start();
//        Phone2 phone = new Phone2();
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
//            phone.hello();
//        },"B").start();
    }
}

class Phone2{

    // synchronized 锁的对象是方法的调用者！
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

    //这里没有锁!不是同步方法，不受锁的影响
    public void hello(){
        System.out.println("Hello");
    }
}
