package com.juc.pool;

import java.util.concurrent.*;

/**
 * 四大策略（拒绝策略）：
 * new ThreadPoolExecutor.AbortPolicy()    // AbortPolicy() 是 ThreadPoolExecutor 默认的拒绝策略 （银行满了，还有人进来，则不处理这个人的，抛出异常）
 * new ThreadPoolExecutor.CallerRunsPolicy()    // CallerRunsPolicy()  哪来的去哪里（从来哪就回哪去执行）
 * new ThreadPoolExecutor.DiscardPolicy()    // DiscardPolicy()  队列满了，丢掉多余的任务，不会抛出异常！
 * new ThreadPoolExecutor.DiscardOldestPolicy()    // DiscardOldestPolicy()  队列满了，尝试和最早的线程竞争（最早的线程如果结束就跟上）如果竞争失败就丢掉多余的任务！也不会抛出异常！
 */
public class Demo01 {
    public static void main(String[] args) {
        // 自定义线程池！工作中要使用 ThreadPoolExecutor 进行操作线程池；Executors 工具类创建线程池不安全！
        // 以银行办理业务为例创建线程池：

        /**
         * 最大线程到底改如何定义：
         * 1、CPU 密集型 电脑/服务器 几核，就是几，可以保持CPU的效率最高！
         * 2、IO  密集型 判断程序中十分耗IO的线程，只要大于这些线程就行了，一般设置这写线程的2倍（一个程序中15个大型任务，IO十分占用资源，那就设置最大线程为30即可）
         */
        //获取CPU 的核数，并打印
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,                              // 核心线程数量
                Runtime.getRuntime().availableProcessors(),                          // 最大线程数量
                3,                           // 超时时间，超时了就会释放
                TimeUnit.SECONDS,                        // 超时单位
                new LinkedBlockingQueue<>(3),   //  LinkedBlockingQueue 链表阻塞队列 （相当于银行的等候区，先来的先办理，链表结构）
                Executors.defaultThreadFactory(),       // Executors 中的 defaultThreadFactory 默认线程工厂
                new ThreadPoolExecutor.DiscardOldestPolicy()    // DiscardOldestPolicy()  队列满了，尝试和最早的线程竞争（最早的线程如果结束就跟上）如果竞争失败就丢掉多余的任务！也不会抛出异常！
        );
        try {
            //核心线程承载数量 5 = 核心线程数量 + 队列设置的数量
            //最大承载线程数量 8 = 最大线程数量 + 队列设置的数量

            /**
             * 当使用线程池开启的线程，没有超过核心线程承载数量时，始终都是用核心线程执行
             * 当使用线程池开启的线程，超过核心线程承载数量，但没超过最大承载线程数量时，除了核心线程执行外，还会根据实际情况增加新的线程执行
             * 当使用线程池开启的线程，超过最大承载线程数量时，就会报错，抛异常(异常可以通过拒绝策略控制)：java.util.concurrent.RejectedExecutionException
             * java.util.concurrent.RejectedExecutionException: Task com.juc.pool.Demo01$$Lambda$1/1831932724@7699a589
             * rejected from java.util.concurrent.ThreadPoolExecutor@58372a00[Running, pool size = 5, active threads = 4, queued tasks = 1, completed tasks = 2]
             * 	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2063)
             * 	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:830)
             * 	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1379)
             * 	at com.juc.pool.Demo01.main(Demo01.java:30)
             */

            for (int i = 0; i < 9; i++) {
                // new Thread(()->{}).start(); //传统方法
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"ok");
                }); //使用线程池后，用线程池来创建线程，执行 Runnable
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //线程池用完，程序结束，要关闭线程池
            threadPool.shutdown();
        }

    }
}
