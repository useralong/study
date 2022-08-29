package com.juc.demo01;

import java.util.concurrent.TimeUnit;

public class Test1 {
    public static void main(String[] args) {
        //new Thread().start();
        //获取cpu的核数
        //CPU 密集型，IO 密集型
        System.out.println(Runtime.getRuntime().availableProcessors());

        try {
            TimeUnit.DAYS.sleep(1);//睡眠一天
            TimeUnit.SECONDS.sleep(1);//睡眠一秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
