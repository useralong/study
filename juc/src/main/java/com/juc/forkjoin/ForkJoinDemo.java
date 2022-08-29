package com.juc.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 求和计算
 * 程序员分3、6、9等
 * 普通的用 循环计算，厉害的用 ForkJoin，牛逼的用 Stream 并行流
 *
 *  如何使用 ForkJoin
 *  1、ForkJoinPool 通过它来执行
 *  2、计算任务 ForkJoinPool.execute(ForkJoinTask<?> task)
 *  3、计算类要继承 RecursiveTask （设置泛型的类型，这个泛型就是返回值的类型）
 *
 *  ForkJoinDemo 继承了 ForkJoinTask，那么 ForkJoinDemo 就是一个计算任务
 */
public class ForkJoinDemo extends RecursiveTask<Long>{

    //int 类型比较小，只有20亿，所以用 long
    private long start; // 1
    private long end; // 1990900000

    //临界值 (可以调节)
    private long temp = 10000L;

    //构造方法
    public ForkJoinDemo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    //实现 RecursiveTask 父类的抽象方法 compute，返回值就是继承父类时设置的泛型
    //计算方法
    @Override
    protected Long compute() {
        if((end-start)<temp){ // 普通循环计算
            Long sum = 0L;
            for (Long i = start; i <= end ; i++) {
                sum += i;
            }
            return sum;
        }else { // ForkJoin 递归
            long middle = (start + end) / 2; // 中间值（将需要计算的值一分为二）
            //通过中间值将 一个任务，拆成两个任务！
            ForkJoinDemo task1 = new ForkJoinDemo(start, middle);
            task1.fork(); // 拆分任务，把任务压入线程队列
            ForkJoinDemo task2 = new ForkJoinDemo(start, middle);
            task2.fork(); // 拆分任务，把任务压入线程队列
            //分支合并计算 ForkJoin   返回结果
            return task1.join() + task2.join();
        }
    }

}
