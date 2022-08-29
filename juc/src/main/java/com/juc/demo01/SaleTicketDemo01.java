package com.juc.demo01;

/**
 * 基本的卖票例子
 *
 * 真正的多线程开发，公司中的开发（一定要降低耦合性）
 * 线程就是一个单独的资源类，没有任何附属的操作！
 * 1、属性、方法
 */
public class SaleTicketDemo01 {
    public static void main(String[] args) {
        //并发：多线程操作同一个资源类 (这里三个线程操作同一个资源类)
        Ticket ticket = new Ticket();

        //Runnable 接口
        //@FunctionalInterface 函数式接口；(可以new Runnable 使用匿名内部内，也可以使用jdk1.8新特性 Lambda表达式 ()->{} )
        //new Thread(Lambda表达式调用Runnable接口实现资源类中的方法,"线程名字").start();
        new Thread(()->{
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        },"C").start();
    }
}

// 单独的资源类 OOP
class Ticket{
    //里面有：属性、方法

    //50张票的属性
    private int number = 50;

    //卖票的方式
    //synchronized 本质：队列 锁
    //如果sale()不加上synchronized，就不是同步方法，卖票时会出现多个线程抢同一个资源的情况，导致卖票结果不能正常递减
    public synchronized void sale(){
        if (number > 0 ){
            System.out.println(Thread.currentThread().getName()+"出售第"+number--+"张票");
        }
    }

}
