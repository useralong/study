package com.juc.add;

import java.util.concurrent.CountDownLatch;

// 计数器 （有加法和减法计数器，CountDownLatch是减法计数器）
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        //new CountDownLatch(6) 需要填一个int 类型的初始值
        //总数是6 （必须要执行任务的时候，再使用！）
        CountDownLatch countDownLatch = new CountDownLatch(6);
        //countDownLatch.countDown(); // countDownLatch.countDown() 方法让总数 -1

        //写一个循环，循环6次，与 new CountDownLatch(6) 初始值相等
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                //相当于教室里面有六个人，循环一次出门一个
                System.out.println(Thread.currentThread().getName()+"出门");
                countDownLatch.countDown(); // 数量 -1
            }).start();
        }
        countDownLatch.await(); //等待计数器归零，然后再向下执行！（不然计数器可能没执行完就执行下面的代码了）

        //等待循环完毕，计数器归零，然后执行关门；教室里面有六个人 都出来了，就关门
        System.out.println("关门");
    }
}
