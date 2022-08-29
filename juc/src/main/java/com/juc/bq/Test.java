package com.juc.bq;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Test {
    //在JAVA中静态方法中只能调用其他,静态方法。main方法都是静态方法,如果想调用其它的方法,要么只能是其它的静态方法。
    public static void main(String[] args) throws InterruptedException {
        //test1();
        //test2();
        //test3();
        test4();

    }
    /**
     * 抛出异常
     */
    public static void test1(){
        // 设置队列的大小：new ArrayBlockingQueue<>(3)
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        //(写) 队列大小为3，添加3个字母，并打印返回信息，如果为true代表添加成功！
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        //如果超出队列的大小3，再添加一个元素，要么是有问题，要么会报错队列已满（这里抛出异常：java.lang.IllegalStateException: Queue full）
        //System.out.println(blockingQueue.add("d"));

        System.out.println(blockingQueue.element()); //查看队首元素是谁
        System.out.println("===========================");

        //(取) remove是没有参数的，取值都是按照【谁先进队列，谁就先出队列的规则】进行取值
        //正常取值也应该是 a,b,c 这个顺序
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        //如果超出队列的大小3，再取出一个元素，会报错没有这个元素（这里抛出异常：java.util.NoSuchElementException）
        //System.out.println(blockingQueue.remove());

    }

    /**
     * 不会抛出异常，
     * 有返回值
     */
    public static void test2(){
        // 设置队列的大小：new ArrayBlockingQueue<>(3)
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        //(写) 队列大小为3，添加3个字母，并打印返回信息，如果为true代表添加成功！
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));

        //如果超出队列的大小3，再添加一个元素，返回false 不抛出异常
        System.out.println(blockingQueue.offer("d")); //false 不抛出异常

        System.out.println(blockingQueue.peek()); //查看队首元素是谁
        System.out.println("===========================");

        //(取) poll是没有参数的，取值都是按照【谁先进队列，谁就先出队列的规则】进行取值
        //正常取值也应该是 a,b,c 这个顺序
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        //如果超出队列的大小3，再取出一个不存在的元素，返回null 不抛出异常
        System.out.println(blockingQueue.poll()); //返回null 不抛出异常
    }

    /**
     * 等待，阻塞（一直阻塞）
     */
    public static void test3() throws InterruptedException {
        // 设置队列的大小：new ArrayBlockingQueue<>(3)
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        //(写) 队列大小为3，添加3个字母
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");

        //如果超出队列的大小3，再添加一个元素，队列没有位置了，等待 会一直阻塞
        //blockingQueue.put("d");

        System.out.println("===========================");

        //(取) take是没有参数的，取值都是按照【谁先进队列，谁就先出队列的规则】进行取值
        //正常取值也应该是 a,b,c 这个顺序
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());

        //如果超出队列的大小3，再取出一个元素，等待 会一直阻塞
        //System.out.println(blockingQueue.take());
    }

    /**
     * 等待，阻塞（等待超时）
     */
    public static void test4() throws InterruptedException {
        // 设置队列的大小：new ArrayBlockingQueue<>(3)
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        //(写) 队列大小为3，添加3个字母，并打印返回信息，如果为true代表添加成功！
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));

        //如果超出队列的大小3，再添加一个元素，等待超过 2 秒就退出
        //System.out.println(blockingQueue.offer("d",2, TimeUnit.SECONDS)); //设置 超时 2 秒

        System.out.println("===========================");

        //(取) poll是没有参数的，取值都是按照【谁先进队列，谁就先出队列的规则】进行取值
        //正常取值也应该是 a,b,c 这个顺序
        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();

        //如果超出队列的大小3，再取出一个不存在的元素，等待超过 2 秒就退出
        //blockingQueue.poll(2,TimeUnit.SECONDS); //设置 超时 2 秒
    }
}
