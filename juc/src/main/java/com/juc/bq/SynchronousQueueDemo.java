package com.juc.bq;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 同步队列
 * 和其他的 BlockingQueue 不一样，SynchronousQueue 不存储元素
 * put（写） 了一个元素，比如从里面先 take（拿/取） 出来，否则不能再继续 put（写） 进去值
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        //SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();//同步队列

        // SynchronousQueue 在 BlockingQueue 中，所以拿过来也不会报错
        // 队列中记得把泛型写上，约束元素的类型，这是一个规范
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();//同步队列

        new Thread(()->{
            try {
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+"put 1");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+"put 2");
                blockingQueue.put("3");
                System.out.println(Thread.currentThread().getName()+"put 3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T1").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3); //睡眠3秒，方便测试同步队列的特性（同步队列中进去一个元素，必须等待取出来之后，才能再往里面放一个元素）
                System.out.println(Thread.currentThread().getName()+"====>"+blockingQueue.take());
                TimeUnit.SECONDS.sleep(3); //睡眠3秒
                System.out.println(Thread.currentThread().getName()+"====>"+blockingQueue.take());
                TimeUnit.SECONDS.sleep(3); //睡眠3秒
                System.out.println(Thread.currentThread().getName()+"====>"+blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T2").start();
    }
}
