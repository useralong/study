package com.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// synchronized
public class Demo02 {
    public static void main(String[] args) {
        Phone2 phone = new Phone2();
        new Thread(()->{
            phone.sms();

        },"A").start();
        new Thread(()->{
            phone.sms();
        },"A").start();
    }
}

//打电话
class Phone2{

    Lock lock = new ReentrantLock();// 细节问题：调用sms(),sms再调用call相当于 lock 有两把锁

    //// sms 方法有一把锁，call方法上也有一把锁，进入 sms 并调用 call，不仅可以拿到 sms 的锁，还可以拿到 call 的锁
    public void sms(){
        lock.lock(); // 细节问题：sms()的 lock.lock();  lock.unlock(); 这是一对儿钥匙
        // lock 锁必须配对【lock.lock();  lock.unlock();】，否则会死锁
        try {
            System.out.println(Thread.currentThread().getName()+"sms");
            // sms方法 调用 call方法
            call(); // 这里sms 调用 call 相当于是一个包含的关系，sms的锁包含了 call的锁；所以A走完才是B
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void call(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"call");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
