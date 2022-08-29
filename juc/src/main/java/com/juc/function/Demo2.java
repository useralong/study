package com.juc.function;

import java.util.function.Predicate;

/**
 * 断定型接口：有一个输入参数，返回值只能是 布尔值！
 */
public class Demo2 {
    public static void main(String[] args) {
        // 用 Predicate 通过匿名内部类 写一个 ”判断字符串十否为空“ 的方法；new Predicate<String> 这里的泛型是设置接收参数的类型
        // Predicate 的 test 接口 实现方法：接收一个 String 类型的值，返回一个 boolean 值
//        Predicate<String> predicate = new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.isEmpty();
//            }
//        };
        //使用 Lambda 表达式简化
//        Predicate<String> predicate = (s) -> {return s.isEmpty();};
        Predicate<String> predicate = s -> {return s.isEmpty();};
        boolean test = predicate.test("");
        System.out.println(test);

    }
}
