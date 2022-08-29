package com.juc.lock;

// synchronized
public class Demo01 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(()->{
            phone.sms();

        },"A").start();
        new Thread(()->{
            phone.sms();
        },"A").start();
    }
}

//打电话
class Phone{

    // sms 方法有synchronized，call方法上也有synchronized
    public synchronized void sms(){
        System.out.println(Thread.currentThread().getName()+"sms");
        // 进入 sms方法 再调用 call方法；不仅可以拿到 sms 的锁，还可以拿到 call 锁 （ synchronized只有一把锁？）
        call(); // 这里sms 调用 call 相当于是一个包含的关系，sms的锁包含了 call的锁；所以A走完才是B
    }
    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"call");

    }
}
