package com.juc.single;

import java.lang.reflect.Constructor;

// 静态内部类
public class Holder {
    // 构造器私有
    private Holder(){

    }
    public static Holder getInstance(){
        return InnerClass.HOLDER; //直接调用静态内部类的变量就行了，但是这种方式也是不安全的
    }

    public static  class InnerClass{
        private static final Holder HOLDER = new Holder();
    }

}
