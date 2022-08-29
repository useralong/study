package com.juc.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

//懒汉式单例
//
public class LazyMan {

    // 定义一个变量 flag 为 false
    private static boolean flag = false;

    //构造器私有
    private LazyMan(){
        //给 懒汉式单例类的 class 加锁（构造器方法上不能使用 synchronized 关键字，只能在方法中使用 synchronized 代码块）
        synchronized (LazyMan.class){ // 由于只能使用 synchronized 代码块，且 LazyMan 对象也是空的，所以只能 锁 class 类模板去判断
            //判断 flag 这个标志位，第一次判断 =false 时设置为true,
            //后面 flag 都为 true 就直接抛出异常
            if(flag == false){ //反射判断的第二种方式
                flag = true;
            }else {
                throw new RuntimeException("不要视图使用反射破坏异常！");
            }

            // 判断 lazyMan 对象是否创建，（）已经创建了则抛出异常
//            if(lazyMan!=null){ //反射判断的第一种方式
//                throw new RuntimeException("不要视图使用反射破坏异常！");
//            }
        }
        System.out.println(Thread.currentThread().getName() + "ok");
    }

    //给 lazyMan 加上 volatile 避免指令重排
    private volatile static LazyMan lazyMan;

    //双重检测锁模式的 懒汉式单例 也就是 DCL 懒汉式
    public static LazyMan getInstance(){
        if(lazyMan==null){
            // lazyMan == null 时，加锁，锁class 然后再判断一次
            synchronized (LazyMan.class){
                if(lazyMan == null){
                    lazyMan = new LazyMan(); // 不是一个原子性操作
                    /**
                     * 不是原子性操作会经过以下步骤：
                     * 1、分配内存空间
                     * 2、执行构造方法，初始化对象
                     * 3、把这个对象指向这个空间
                     *
                     * 可能出现的后果：
                     * 我们期望按照123步骤执行，但是真实情况可能会出现132情况
                     * 加入 A 线程执行132步骤，突然来了个线程B,
                     * 那么会出现没有初始化对象，但是对象已经指向了这个空间；然后B线程对 lazyMan==null 的判断就没用了
                     * 就会直接 return ，但是此时 lazyMan 还没有完成构造初始化对象
                     *
                     * 解决方式：
                     * 给 lazyMan 加上 volatile 避免指令重排
                     * 双重检测锁 + 原子性操作 就不会被指令重排了
                     */
                }
            }
        }
        return lazyMan;
    }

    //单线程下 单例确实OK
    //测试多线程并发下：
//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            new Thread(()->{
//                LazyMan.getInstance(); // 静态变量通过类名调用，这里是静态方法
//            }).start();
//        }
//    }


    /**
     *  正常：单例模式确保某个类只有一个实例,而且自行实例化并向整个系统提供这个实例
     *  不安全情况：出现两个及以上的对象实例
     *
     *  1、我们使用 LazyMan.getInstance() 创建第一个对象实例后，再用 class 反射去创建一个对象；
     *  结果：发现可以创建两个对象实例
     *  解决：在 构造器中对 class 类模板 加了锁；已经存在的单例模式的对象实例后，就不能使用 class 反射创建第二个对象实例
     *
     *  2、我们用 class 反射去创建两个对象
     *  结果：发现创建成功了
     *  解决：定义一个 名称为 flag 的 boolean 值 为 false 的变量，在 class 类模板 加锁的代码块中判断；为 false 就改为 true，不为 false 就抛异常
     *  （相当于一开始 flag=false 然后通过 if 判断，改为了 true；后面再判断时，就直接 else 抛异常了）
     *  前提：方法2，必须是通过反编译创建两个及以上的对象实例才有用，因为不通过反编译是找不到 我们定义标志位的关键字的，也就不可能拿来做判断了！
     *
     *  3、我们用 class 反射去创建两个对象,并且用 class 反射 在第一个对象创建后 把 flag 这个标志位的值 改为 false，
     *  结果：方法2的延申，我们发现，又可以创建两个对象了
     *  解决：我们使用枚举类的单例模式
     *
     */
    // 反射
    public static void main(String[] args) throws Exception {
//        LazyMan instance = LazyMan.getInstance();
        // LazyMan.class.getDeclaredConstructor 通过 class 反射 获取空参构造器

        Field flag = LazyMan.class.getDeclaredField("flag"); //通过反射 拿到 flag 标志位字段
        flag.setAccessible(true);

        Constructor<LazyMan> declaredConstructor = LazyMan.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true); // 无视了私有的构造器(反射对象在使用的时候，不会去检查Java语言权限控制)
        LazyMan instance = declaredConstructor.newInstance();

        flag.set(instance,false); //我们将第一个 instance 对象 中的 flag 的值改为 false

        LazyMan instance2 = declaredConstructor.newInstance();

        System.out.println(instance);
        System.out.println(instance2);


    }
}
