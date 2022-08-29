package com.juc.function;

import java.util.function.Function;

/**
 * Function 函数型接口，有一个输入参数，和一个输出参数
 * 只要是 函数式接口 可以用 Lambda 表达式简化
 */
public class Demo01 {
    public static void main(String[] args) {
        //  用 函数式接口 Function 通过匿名内部类 写一个工具类
        // 工具类：输出输入的值

        // Function<T,R> 泛型，传入 T （接收参数类型）,返回 R （返回值类型）
        //通过匿名内部类调用 Function 接口的 apply 实现方法：接收一个 String 类型的值，返回一个 String 类型的值
//        Function function = new Function<String,String>() {
//            @Override
//            public String apply(String str) {
//                return str;
//            }
//        };
        //使用 Lambda 表达式简化
//        Function function = (str) -> {return str;};
        Function function = str -> {return str;};

        String apply = (String) function.apply("abc");
        System.out.println(apply);
    }
}
