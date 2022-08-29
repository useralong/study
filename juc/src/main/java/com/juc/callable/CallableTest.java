package com.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) {
        // new Thread(new Runnable()).start(); 正常线程执行，调用 Runnable 接口实现类
        // new Thread(new FutureTask<V>()).start(); Runnable 接口有一个FutureTask<V> 实现类可以实现 Runnable接口
        // new Thread(new FutureTask<V>( Callable )).start(); FutureTask<V> 实现类 不仅可以调用 Callable，还能调用 Runnable 去实现

        MyThread thread = new MyThread(); // Callable 实现类
        //适配类，（FutureTask） 让 Callable 可以通过 Runnable 在线程里执行
        FutureTask futureTask = new FutureTask(thread);
        // Thread(futureTask).start(); //通过适配类启动 Callable
        //两个线程执行，会打印几个 call() 和 1024 呢？ 只打印一个，结果会被缓存，效率高！
        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();
        try {
            // futureTask.get() 这个get方法可能会产生阻塞！(可能会遇见耗时的线程) 把它放到最后
            // 或者使用异步通信来处理
            Integer i = (Integer) futureTask.get();////通过适配类，拿到 Callalble 的返回值
            System.out.println(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}

//创建一个线程类，实现 Callable<T> 接口
// Callable<T> 接口 中泛型的参数，等于 Callable 接口中 call() 方法的返回值类型
class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("call()");
        return 1024;
    }
}
