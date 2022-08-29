package com.juc.add;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        /**
         * 集齐七颗龙珠召唤神龙
         * CyclicBarrier 两种使用方法
         * 1、CyclicBarrier(int)   直接设置加法计数器的上限
         * 2、CyclicBarrier(int，Runnable)    不仅可以设置加法计数器的上限，还可以直接在计数器中使用 Runnbale 接口执行线程
         * 注意：如果线程执行的数量，没有达到，预先设置的加法计数器的上限，那么线程执行到最后会一直等待（等待执行线程的数量达到加法计数器的上限）
         */
        //召唤神龙的线程
        //CyclicBarrier cyclicBarrier = new CyclicBarrier(7);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            //CyclicBarrier加法计数器，每执行完一个线程就会自动 +1

            //这里执行 计数达到预先设置的值 后执行的代码
            System.out.println("召唤神龙成功！");
        });

        for (int i = 1; i <= 7; i++) {
            final int temp = i; // 使用 final 常量关键字 设置一个 变量 重新获取一下 i 的值，lambda 表达式中就能获取了
            // lambda 能操作到循环中的 i 吗？
            // lambda 表达式本质上是 new 了一个类，是操作不到 for 循环中的变量 i
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集"+temp+"个龙珠");

                try {
                    cyclicBarrier.await();// 等待，等待循环线程执行完毕（也就是让加法计数器一直等待，计数达到预先设置的值，然后开始执行后面的代码）
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
