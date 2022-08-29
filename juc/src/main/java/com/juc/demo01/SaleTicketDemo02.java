package com.juc.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基本的卖票例子
 *
 * 真正的多线程开发，公司中的开发（一定要降低耦合性）
 * 线程就是一个单独的资源类，没有任何附属的操作！
 * 1、属性、方法
 */
public class SaleTicketDemo02 {
    public static void main(String[] args) {
        //并发：多线程操作同一个资源类 (这里三个线程操作同一个资源类)
        Ticket2 ticket = new Ticket2();

        //Runnable 接口
        //@FunctionalInterface 函数式接口；(可以new Runnable 使用匿名内部内，也可以使用jdk1.8新特性 Lambda表达式 ()->{} )
        //new Thread(Lambda表达式调用Runnable接口实现资源类中的方法,"线程名字").start();
        new Thread(()->{ for (int i = 1; i < 40; i++) { ticket.sale(); } },"A").start();
        new Thread(()->{ for (int i = 1; i < 40; i++) { ticket.sale(); } },"B").start();
        new Thread(()->{ for (int i = 1; i < 40; i++) { ticket.sale(); } },"C").start();
    }
}

// 单独的资源类 OOP
//Lock 三步曲
//1. Lock lock = new ReentrantLock();
//2. lock.lock(); //枷锁
//3. lock.unlock(); //解锁
//注意：需要枷锁的代码放进 try 语句块，加锁的代码在 try 语句块外面，解锁的代码在 finally 块中
class Ticket2{
    //里面有：属性、方法

    //50张票的属性
    private int number = 50;

    //new 一个可重入锁 ReentrantLock()  声明 lock
    Lock lock = new ReentrantLock();

    //卖票的方式
    public void sale(){
        //使用可重入锁，在需要上锁的代码的上方，进行加锁
        lock.lock();
        //将需要加锁的业务代码，用try catch 包起来
        try {
            if (number > 0 ){
                System.out.println(Thread.currentThread().getName()+"出售第"+number--+"张票");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //try catch 包起来需要加锁的代码
            //finally { } 中加入解锁（执行完 try 语句块，再执行 finally 解锁；不管有没有抛出异常 finally 都会执行）
            lock.unlock();
        }
    }

}
