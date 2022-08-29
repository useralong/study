package com.juc.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 */
public class SpinlockDemo {

    //使用原子引用 AtomicReference
    //泛型设置默认值类型，默认值：int 类型 为 0，对象 为 null（如:Thread 线程类为 null）
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    //加锁
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"==> myLock");
        //自旋锁（用 while 或 do while 都是一样的）
        while (!atomicReference.compareAndSet(null,thread)){
            //compareAndSet()：比较并交换；期望的值是 null，那么就把这个值改成当前的线程
            //while判断 不为 true
        }
    }

    //解锁
    public void myUnLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"==> myUnLock");
        //compareAndSet()：比较并交换；期望的值是 当前的 线程 thread，那么就把这个值改成null，让myLock加锁方法无法进行 while循环，就解锁的
        atomicReference.compareAndSet(thread,null);

    }


}
