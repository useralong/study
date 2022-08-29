package com.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 解决 ABA 问题
 * 引入  原子引用！对应的思想：乐观锁
 * AtomicReference
 * AtomicStampedReference
 */
public class CASDemo {

    // CAS  compareAndSet()：比较并交换！
    public static void main(String[] args) {
        //AtomicInteger atomicInteger = new AtomicInteger(2020);// 给 AtomicInteger 原子类设置一个初始值 2020

        //new AtomicReference<>();
        //new AtomicStampedReference<>(初始值,时间戳 相当于初始的版本号)
        //初始值如果是int类型，由于Integer包装类的问题，初始值不能设置太大
        //AtomicStampedReference 注意：如果 泛型是一个包装类，注意对象的引用问题！
        //正常的业务操作 AtomicStampedReference 进行比较的泛型是一个个对象
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(20, 1);

        // a线程的操作，b线程要知道才行

        //每次进入线程，先获取一些版本号，和mysql的乐观锁一模一样
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp(); //获取版本号
            System.out.println("a1=>"+stamp);

            try {
                TimeUnit.SECONDS.sleep(2); //延时2秒，让a,b线程进入的时候获取的版本号一样
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /**
             * 乐观锁执行完，会把 version +1
             *
             * 乐观锁是对于数据冲突保持一种乐观态度，操作数据时不会对操作的数据进行加锁（这使得多个任务可以并行的对数据进行操作），
             * 只有到数据提交的时候才通过一种机制来验证数据是否存在冲突(一般实现方式是通过加版本号然后进行版本号的对比方式实现)
             */
            //延时后 使用 atomicStampedReference 进行比较并交换操作；
            System.out.println(atomicStampedReference.compareAndSet(
                    20, 22,      // 与期望的初始值20比较，如果20比较相同，就改成22
                    atomicStampedReference.getStamp(),  //获取最新的版本号
                    atomicStampedReference.getStamp() + 1)+"==a2"); //比较并交换操作后，版本号需要+1

            System.out.println("a2=>"+atomicStampedReference.getStamp()); //获取最新的版本号

            System.out.println(atomicStampedReference.compareAndSet(
                    22, 20,      // 与期望(更改后)的22比较，如果22比较相同，就改回20
                    atomicStampedReference.getStamp(),  //获取最新的版本号
                    atomicStampedReference.getStamp() + 1)+"==a3"); //比较并交换操作后，版本号需要+1

            System.out.println("a3=>"+atomicStampedReference.getStamp()); // 获取最新的版本号；到a3这里，通过打印发现，虽然最终的值和初始值一样；但是每次更改后版本也在改变
        },"a").start();

        //乐观锁的原理相同！
        //我们可以看见最终打印结果，b2修改失败
        //（因为a2、a3的修改，让版本号已经不再是初始的 1 了，而b2修改的时候调用的版本号还是之前的，不是最新的，所以修改失败）

        /**
         * 乐观锁的原理相同！
         *
         * 乐观锁执行完，会把 version +1
         * 乐观锁是对于数据冲突保持一种乐观态度，操作数据时不会对操作的数据进行加锁（这使得多个任务可以并行的对数据进行操作），
         * 只有到数据提交的时候才通过一种机制来验证数据是否存在冲突(一般实现方式是通过加版本号然后进行版本号的对比方式实现)
         *
         * 我们这里 A B 两个线程任务并行进行CAS操作
         * 可以看见最终打印结果，b2修改失败 （因为加了版本号后进行了版本号对比方式实现）
         */
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp(); //获取版本号
            System.out.println("b1=>"+stamp);

            try {
                TimeUnit.SECONDS.sleep(2); //延时2秒，让a,b线程进入的时候获取的版本号一样
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(atomicStampedReference.compareAndSet(
                    20, 23,      // 与期望的初始值20比较，如果20比较相同，就改成23
                    atomicStampedReference.getStamp(),  //获取最新的版本号
                    atomicStampedReference.getStamp() + 1)+"==b2"); //比较并交换操作后，版本号需要+1

            System.out.println("b2=>"+atomicStampedReference.getStamp()); //获取最新的版本号（这里的版本号可能是在a3版本号的基础上+1了）
        },"b").start();

    }
}
