package com.juc.function;


import java.util.function.Consumer;

/**
 * Consumer 消费型接口：只有输入，没有返回值！
 */
public class Demo3 {
    public static void main(String[] args) {

        // 用 Consumer 通过匿名内部类 写一个 ”打印字符串“ 的方法；new Consumer<泛型> 这里的泛型是设置接收参数的类型
        // Consumer 的 accept 接口 实现方法：接收一个 String 类型的值，不返回值
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        };
        //使用 Lambda 表达式简化
        Consumer<String> consumer = (str) -> { System.out.println(str); };
//        Consumer<String> consumer = str -> { System.out.println(str); };
        consumer.accept("ad");
    }
}
