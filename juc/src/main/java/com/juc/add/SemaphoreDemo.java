package com.juc.add;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        // new Semaphore("设置默认的线程数量") ：停车位举例！
        // Semaphore 主要是用来限流！
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                //acquire() 得到（获取阻塞）
                //release() 释放（添加许可证释放阻塞）
                try {
                    semaphore.acquire(); //阻塞这个线程
                    System.out.println(Thread.currentThread().getName()+"抢到车位"); //因为抢到了这个车位，所以这个线程被占用了（阻塞）
                    TimeUnit.SECONDS.sleep(2); //阻塞2秒（占用这个车位2秒后离开车位）
                    System.out.println(Thread.currentThread().getName()+"离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //try 语句块执行完后需要进行释放，则在 finally 语句块中进行 这个线程的释放！
                    semaphore.release();//释放阻塞
                }

                /**
                 * 1、finally语句中的代码段始终要被执行
                 * 2、在try catch块里有return的时候，finally也会被执行；try或者catch代码块中的返回值会先被保留，再来执行finally代码块中的语句，等到finally代码块执行完毕之后，在把之前保留的返回值给返回出去。
                 * 3、如果finally里有return语句的话，会把try catch块里的return语句效果给覆盖掉
                 */
            },String.valueOf(i)).start();
        }
    }
}
