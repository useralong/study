package com.juc.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 使用 Future 中的 CompletableFuture 类 实现异步调用
 * 异步调用：发起一个请求，不一定要立即获取这个结果；可以先执行其他的，等这个请求执行好后再返回给我们
 * 异步执行
 *   成功回调
 *   失败回调
 */
public class Demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 发起一个请求
        // 正常的多线程是没有返回值的；使用 CompletableFuture 异步回调，可以自定义设置返回值
        // runAsync() 异步完成，可以通过 Runnable、Executor 来执行

        // 没有返回值的 runAsync 异步回调
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
//            // 当前程序执行，不会占用其他程序执行！
//            try {
//                TimeUnit.SECONDS.sleep(2); //休眠2秒
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName()+"runAsync=>Void");
//        });
//        System.out.println("111");
//        completableFuture.get(); // 获取阻塞的执行结果


        // supplyAsync 里面接收的是 Supplier 供给型函数式接口，没有参数，只有返回值

        // 有返回值的 supplyAsync 异步回调
        // 异步调用，返回的是成功和失败的回调；成功返回信息，失败返回错误信息
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"supplyAsync=>Integer");
//            int i = 10/0; // 制造一个错误（正常情况分母不能为0）
            return 200;
        });
        // whenComplete 里面接收的是 BiConsumer 加强版的消费型函数式接口，有参数，没有返回值
        // whenComplete() 返回与此阶段相同的结果或异常
        Integer response = completableFuture.whenComplete((t, u) -> {
            System.out.println("t=>" + t); // 参数t：正常的返回结果
            System.out.println("u=>" + u); // 参数u：错误信息
            // exceptionally 里面接收的是 Function 函数式接口，有参数，有返回值（参数和返回值类型不一样时可以做类型转换）
            // exceptionally() 返回一个新的 CompletableFuture ，结果是异常触发(可以接收异常，并且给出失败的返回值)
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return 500; //错误的返回结果
        }).get();

        System.out.println(response);
        // 程序往下执行的时候，遇见异步的请求（不管这个异步请求是否阻塞），肯定是先往下执行打印 111，再获取异步的结果

    }
}
