package com.juc.pc;

/**
 * 线程之间的通信问题：生产者和消费者问题！  等待唤醒，通知唤醒
 * 线程交替执行  （A 和 B两个线程操作同一个变量  num=0）
 * A 操作num + 1
 * B 操作num - 1
 * A 和 B两个线程要产生通信才能做到 A 操作num+ 1后 通知 B 操作num - 1
 */
public class A {
    public static void main(String[] args) {
        Data data = new Data();

        //Runnable 接口
        //@FunctionalInterface 函数式接口；(可以new Runnable 使用匿名内部内，也可以使用jdk1.8新特性 Lambda表达式 ()->{} )
        //new Thread(Lambda表达式调用Runnable接口实现资源类中的方法,"线程名字").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start(); //A 线程+1
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start(); //B 线程-1
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start(); //C 线程+1
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start(); //D 线程-1
    }
}

//生产者 消费者的口诀：判断等待、业务、通知！

//数字  资源类
class Data{
    private int number = 0;

    //+1
    public synchronized  void increment() throws InterruptedException {
        //判断number 不等于0 的时候等待 -1的通知；反之等于0 则直接 +1
        while(number != 0){
            //等待 -1的通知
            this.wait();
        }
        //业务
        number++;
        System.out.println(Thread.currentThread().getName()+"=>"+number);
        //通知其他线程，我+1完毕
        this.notifyAll();
    }

    //-1
    public synchronized  void decrement() throws InterruptedException {
        //判断number 等于0 的时候等待 +1的通知；反之不等于0 则直接 -1
        while (number == 0){
            //等待 +1的通知
            this.wait();
        }
        //业务
        number--;
        System.out.println(Thread.currentThread().getName()+"=>"+number);
        //通知其他线程，我-1完毕
        this.notifyAll();
    }
}
