package com.juc.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * enum 是什么？本身也是一个 class 类
 */
public enum EnumSingle {

    INSTANCE;

    public EnumSingle getInstance(){
        return INSTANCE;
    }
}


/**
 * 测试枚举类能否保证对象是唯一的？
 *
 * 按照 LazyMan 中的反射测试:
 * 1、我们直接用枚举类 EnumSingle.INSTANCE 创建一个对象实例；再用 class 反射去创建一个对象
 * IDEA 中找到编译后的 EnumSingle 源码，分析一下是有参还是无参构造；IDEA 上看编译后的.class源码也是无参构造，使用 javap -p 命令反编译后从源码上看也是无参构造
 * 结果：报错 java.lang.NoSuchMethodException: com.juc.single.EnumSingle.<init>() 指EnumSingle类里没有空参的构造器方法
 * 解决：最终使用【jad.exe】反编译工具，反编译了 EnumSingle.class 这个源码文件，我们发现其实是有参的构造！有参一个String 一个int（String s,int i）
 * 对 EnumSingle.class.getDeclaredConstructor(null);改造
 * 改造完：EnumSingle.class.getDeclaredConstructor(String.class,int.class);
 * 重新运行抛出异常：Cannot reflectively create enum objects
 * 到这里才算是达到预期，验证了反射确实不能破坏枚举类的单例模式！
 *
 *
 */
class Test{
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        EnumSingle instance = EnumSingle.INSTANCE;
//        EnumSingle instance2 = EnumSingle.INSTANCE;

        Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(String.class,int.class);
        declaredConstructor.setAccessible(true);
        EnumSingle instance2 = declaredConstructor.newInstance();

        //java.lang.NoSuchMethodException: com.juc.single.EnumSingle.<init>()
        System.out.println(instance);
        System.out.println(instance2);
    }
}
