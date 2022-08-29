package com.juc.single;

/**
 * 饿汉式单例模式
 */
public class Hungry {

    //饿汉式会把所有的对象都加载进来；可能会浪费空间
    private byte[] data1 = new byte[1024*1024];
    private byte[] data2 = new byte[1024*1024];
    private byte[] data3 = new byte[1024*1024];
    private byte[] data4 = new byte[1024*1024];

    //构造器私有
    private Hungry(){

    }

    //饿汉式就是一上来就加载这个对象
    //饿汉式就是先不管三七二十一 new一个对象
    private final static Hungry HUNGRY = new Hungry();

    public static Hungry getInstance(){
        return HUNGRY;
    }
}
