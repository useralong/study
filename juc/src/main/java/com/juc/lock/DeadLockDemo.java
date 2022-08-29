package com.juc.lock;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA="lockA";
        String lockB="lockB";

        new Thread(new MyThread(lockA,lockB),"T1").start();
        new Thread(new MyThread(lockA,lockB),"T2").start();
    }
}

class MyThread implements Runnable{

    private String lockA;
    private String lockB;

    public MyThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            //进入lockA 想拿lockB
            System.out.println(Thread.currentThread().getName()+"lock:"+lockA+"=>get:"+lockB);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockB){
                //lockB 也想 拿 lockA
                System.out.println(Thread.currentThread().getName()+"lock:"+lockB+"=>get:"+lockA);

            }

        }
    }
}
