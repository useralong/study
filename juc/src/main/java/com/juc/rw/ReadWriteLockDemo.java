package com.juc.rw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 独占锁（写锁）一次只能被一个线程占有
 * 共享锁（读锁）多个线程可以同时占有
 * ReadWriteLock
 * 读-读 可以共存！
 * 读-写 不能共存！要写完才可以操作别的
 * 写-写 不能共存！要写完才能继续写
 *
 * 报错：
 * java.lang.IllegalMonitorStateException: attempt to unlock read lock, not locked by current thread
 * 原因：尝试解锁读锁，当前线程未锁定
 * 如何解决：
 * 发现代码错误 readWriteLock.writeLock(); readWriteLock.readLock();
 * 正确写法：readWriteLock.writeLock().lock(); readWriteLock.readLock().lock();
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        //如果让线程执行未加锁的自定义缓存类，会出现：写入中，一个线程还没执行完，后面的线程就插队进来了；读取也是这样
        //MyCache myCache = new MyCache();
        MyCacheLock myCache = new MyCacheLock();

        //这五个线程只做 写入
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.put(temp+"",temp+"");
            },String.valueOf(i)).start();
        }

        //这五个线程只做 读取
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.get(temp+"");
            },String.valueOf(i)).start();
        }

    }
}

/**
 * 自定义缓存(加锁的)
 */
class MyCacheLock{
    //volatile 保证原子性
    private volatile Map<String,Object> map = new HashMap<>();
    //声明 ReadWriteLock 读写锁；更加细粒度的控制
    private ReadWriteLock readWriteLock =  new ReentrantReadWriteLock();

    //存、写 （写入时希望同时只有一个线程在写入）可以用 ReadWriteLock
    public void put(String key,Object value){
        //加入ReadWriteLock中的 写锁
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"写入"+key);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"写入ok");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //加入ReadWriteLock中 写锁的解锁
            readWriteLock.writeLock().unlock();
        }
    }

    //取、读 （所有人也就是多个线程都可以读）可以用 ReadWriteLock
    public void get(String key){
        //加入ReadWriteLock中的 读锁
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"读取"+key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName()+"读取ok");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //加入ReadWriteLock中 读锁的解锁
            readWriteLock.readLock().unlock();
        }


    }
}

/**
 * 自定义缓存(未加锁)
 */
class MyCache{
    //volatile 保证原子性
    private volatile Map<String,Object> map = new HashMap<>();

    //存、写
    public void put(String key,Object value){
        System.out.println(Thread.currentThread().getName()+"写入"+key);
        map.put(key,value);
        System.out.println(Thread.currentThread().getName()+"写入ok");
    }

    //取、读
    public void get(String key){
        System.out.println(Thread.currentThread().getName()+"读取"+key);
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName()+"读取ok");

    }
}
