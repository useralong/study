package com.juc.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程之间的通信问题：生产者和消费者问题！  等待唤醒，通知唤醒
 * 线程交替执行  （A 和 B两个线程操作同一个变量  num=0）
 * A 操作num + 1
 * B 操作num - 1
 * A 和 B两个线程要产生通信才能做到 A 操作num+ 1后 通知 B 操作num - 1
 */
public class B {
    public static void main(String[] args) {
        Data2 data = new Data2();

        //Runnable 接口
        //@FunctionalInterface 函数式接口；(可以new Runnable 使用匿名内部内，也可以使用jdk1.8新特性 Lambda表达式 ()->{} )
        //new Thread(Lambda表达式调用Runnable接口实现资源类中的方法,"线程名字").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"A").start(); //A 线程+1
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"B").start(); //B 线程-1
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"C").start(); //C 线程+1
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"D").start(); //D 线程-1
    }
}

//生产者 消费者的口诀：判断等待、业务、通知！

//数字  资源类
class Data2{

    private int number = 0;

    //new 一个可重入锁 ReentrantLock()  声明 lock
    Lock lock = new ReentrantLock();
    //通过 lock.newCondition() 获取 Condition 实例（Condition 实例下有.await()等待 .signal()唤醒 方法）
    Condition condition = lock.newCondition();

//    condition.await(); //等待
//    condition.signalAll(); //唤醒全部

    //+1
    public void increment() {
        //使用可重入锁，在需要上锁的代码的上方，进行加锁
        lock.lock();
        //将需要加锁的业务代码，用try catch 包起来
        try {
            //判断number 不等于0 的时候等待 -1的通知；反之等于0 则直接 +1
            while(number != 0){
                //等待 -1的通知
                condition.await();
            }
            //业务
            number++;
            System.out.println(Thread.currentThread().getName()+"=>"+number);
            //通知其他线程，我+1完毕
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //try catch 包起来需要加锁的代码
            //finally { } 中加入解锁（执行完 try 语句块，再执行 finally 解锁；不管有没有抛出异常 finally 都会执行）
            lock.unlock();
        }
    }

    //-1
    public void decrement() {
        //使用可重入锁，在需要上锁的代码的上方，进行加锁
        lock.lock();
        //将需要加锁的业务代码，用try catch 包起来
        try {
            //判断number 等于0 的时候等待 +1的通知；反之不等于0 则直接 -1
            while (number == 0){
                //等待 +1的通知
                condition.await();
            }
            //业务
            number--;
            System.out.println(Thread.currentThread().getName()+"=>"+number);
            //通知其他线程，我-1完毕
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //try catch 包起来需要加锁的代码
            //finally { } 中加入解锁（执行完 try 语句块，再执行 finally 解锁；不管有没有抛出异常 finally 都会执行）
            lock.unlock();
        }
    }
}
