package com.juc.volatile_test;

import java.util.concurrent.TimeUnit;

public class JMMDemo {

    //给变量加上 volatile 再运行程序，可以发现控制台输出 1 后立马停止了
    //不加 volatile 程序就会死循环 （因为 main 方法中的新开启的线程，感知不到 num 变量已经被修改了）
    //加了 volatile 可以保证可见性
    private volatile static int num = 0; // 定义一个num变量

    public static void main(String[] args) {
        new Thread(()->{
            while (num==0){

            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1); // 休眠1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num = 1;
        System.out.println(num);
    }
}
