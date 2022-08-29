package com.juc.function;

import java.util.function.Supplier;

/**
 * Supplier 供给型接口：没有输入参数，只有返回值！
 */
public class Demo4 {
    public static void main(String[] args) {
        // 用 Supplier 通过匿名内部类 写一个 ”输出返回值“ 的方法；new Supplier<泛型> 这里的泛型是设置返回值类型
        // Supplier 的 get 接口 实现方法：不接收参数，有返回值
//        Supplier<Integer> supplier = new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                System.out.println("get()");
//                return 1024;
//            }
//        };
        //使用 Lambda 表达式简化 Supplier<Integer> 这里的泛型直接限定了返回值的类型
        Supplier<Integer> supplier = () -> { return 1024; };
        System.out.println(supplier.get());
    }
}
