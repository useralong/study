package com.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自旋锁测试结果
 * T1先进锁 打印 myLock，然后 T2也会进去 等着 但会打印 myLock，
 * T1执行完释放后 打印 myUnLock， T2等 T1 执行完毕释放后，也会执行并释放 也会进去 打印 myLock，
 */
public class TestSpinlock {
    public static void main(String[] args) throws InterruptedException {
        //原来使用 Lock 锁的方式
//        ReentrantLock reentrantLock = new ReentrantLock();
//        reentrantLock.lock();
//        try {
//            //需要执行的
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            reentrantLock.unlock();
//        }

        //现在用自己创建的自旋锁CAS（自旋锁就是一个CAS的操作，一定要有CAS）
        SpinlockDemo lock = new SpinlockDemo();

        new Thread(()->{
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);//休息1秒钟
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.myUnLock();
            }
        },"T1").start();

        TimeUnit.SECONDS.sleep(1);//休息1秒钟（保证我们的 t1 线程先获取到锁；t1 线程先获取到锁后，t2线程进来的时候是获取不到的，要再里面等待 t1线程执行完毕先释放锁）

        new Thread(()->{
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);//休息1秒钟
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.myUnLock();
            }
        },"T2").start();

    }
}
