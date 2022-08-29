package com.juc.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现A线程执行完，通知B线程；B线程执行完通知C线程;C线程执行完通知A线程(依次形成一个循环)
 */
public class C {
    public static void main(String[] args) {
        Data3 data = new Data3();

        //Runnable 接口
        //@FunctionalInterface 函数式接口；(可以new Runnable 使用匿名内部内，也可以使用jdk1.8新特性 Lambda表达式 ()->{} )
        //new Thread(Lambda表达式调用Runnable接口实现资源类中的方法,"线程名字").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.printA();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.printB();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.printC();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
    }
}

//资源类 Lock
class Data3{

    //number=1时A执行；number=2时B执行；number=3时C执行
    private int number = 1;
    //new 一个可重入锁 ReentrantLock()  声明 lock
    private Lock lock = new ReentrantLock();
    //lock.newCondition()创建Condition实例
    //使用三个个Condition监视器实例，分别对应A,B,C 三个线程
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();

    //number起始值为1，printA()执行，printB() printC()等待
    //printA()执行完通知printB()，printB()执行；printA() printC()等待
    //printB()执行完通知printC()，printC()执行；printA() printB()等待
    //printC()执行完通知printA()，printA()执行；printB() printC()等待
    //如此循环往复

    public void printA(){
        lock.lock();
        try {
            //业务判断 -> 执行 -> 通知
            while(number != 1){
                //等待
                conditionA.await();
            }
            //A执行
            System.out.println(Thread.currentThread().getName()+"=>AAAAA");
            number = 2;
            //通知（通知唤醒指定的人，B）
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printB(){
        lock.lock();
        try {
            //业务判断 -> 执行 -> 通知
            while(number != 2){
                //等待
                conditionB.await();
            }
            //A执行
            System.out.println(Thread.currentThread().getName()+"=>BBBBB");
            number = 3;
            //通知（通知唤醒指定的人，C）
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printC(){
        lock.lock();
        try {
            //业务判断 -> 执行 -> 通知
            while(number != 3){
                //等待
                conditionC.await();
            }
            //A执行
            System.out.println(Thread.currentThread().getName()+"=>CCCCC");
            number = 1;
            //通知（通知唤醒指定的人，A）
            conditionA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
