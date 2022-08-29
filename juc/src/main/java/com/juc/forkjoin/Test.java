package com.juc.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * 程序员分3、6、9等
 *  普通的用 循环计算，厉害的用 ForkJoin，牛逼的用 Stream 并行流
 *  1
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test1(); // 执行时间：17225
//        test2(); // 执行时间：forkjoin有点小问题，可以执行，最终结果不准 有待考究
        test3(); // 执行时间：638
    }
    //普通程序员
    public static void test1(){
        Long sum = 0L;
        long start = System.currentTimeMillis();
        for (Long i = 1L; i <= 10_0000_0000; i++) { //10_0000_0000 十亿的写法，分割符
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("sum="+sum+"时间："+(end-start));
    }
    //会使用 ForkJoin
    public static void test2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinDemo(0L, 10_0000_0000L);
        //forkJoinPool.execute(task); // 执行任务
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);// 提交一个ForkJoinTask来执行
        Long sum = submit.get(); //get() 获取结果，会阻塞

        long end = System.currentTimeMillis();
        System.out.println("sum="+sum+"时间："+(end-start));
    }
    // Stream 并行流【LongStream、IntStream、DoubleStream】
    public static void test3(){
        long start = System.currentTimeMillis();
        // 用 LongStream
        //range() 返回有序的顺序 LongStream从 startInclusive （含）至 endExclusive通过增量步骤（独家） 1
        //rangeClosed() 返回有序的顺序 LongStream从 startInclusive （含）至 endExclusive通过增量步骤（独家） 1
        //range() 相当于没闭合()，rangeClosed() 相当于闭合了(]

        //parallel() 并行计算（返回平行的等效流）
        //reduce(int identity, LongBinaryOperator op)
        //identity =默认值或初始值。BinaryOperator =功能接口，采用两个值并产生一个新值。如果缺少identity参数，则没有默认值或初始值，并且它返回可选值
        //reduce(0, Long::sum) 中的 Long::sum  引用,自己理解的：设置参数类型（与需要运算的参数一致），通过 sum 进行求和
        //也可以写成 reduce(0, (a,b)->a+b)
        long sum = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, (a,b)->a+b);

        long end = System.currentTimeMillis();
        System.out.println("sum="+sum+"时间："+(end-start));
    }
}
