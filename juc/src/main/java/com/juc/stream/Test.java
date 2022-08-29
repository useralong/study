package com.juc.stream;

import java.util.Arrays;
import java.util.List;

/**
 * 题目要求：一分钟完成此题，只能用一行代码实现！
 * 现在有5个用户：筛选：
 * 1、ID 必须是偶数
 * 2、年龄必须大于23岁
 * 3、用户名转为大写字母
 * 4、用户名字母倒着排序
 * 5、只输出一个用户！
 */
public class Test {
    public static void main(String[] args) {
        User u1 = new User(1, "a", 21);
        User u2 = new User(2, "b", 22);
        User u3 = new User(3, "c", 23);
        User u4 = new User(4, "d", 24);
        User u5 = new User(5, "e", 25);
        User u6 = new User(6, "f", 26);
        User u7 = new User(7, "g", 27);
        User u8 = new User(8, "q", 28);
        //将多个对象转为集合，集合就是存储
        List<User> list = Arrays.asList(u1, u2, u3, u4, u5,u6,u7,u8);
        //计算交给 Stream 流
        //用到了：Lambda表达式、链式编程、函数式接口、Stream流式计算
        list.stream()
                //x % 2 == 0，x 模 2 取余数：没余数结果等于0代表偶数，有余数等于1代表奇数，x 等于 0 就是偶数，不等于就是奇数
                .filter(u ->{ return u.getId() % 2 == 0; })     // filter() 过滤 (使用了 Predicate 断定型接口)，过滤 id 为偶数的
                .filter(u->{return u.getAge() > 23;})       // filter() 过滤 (使用了 Predicate 断定型接口)，过滤 age 大于 23的
                .map(u->{return u.getName().toUpperCase();})    // map() 转换 （使用了 Function 函数型接口），将 Name 的小写英文，转成大写 （也可以将某一个类型的数据转换成另一个类型）
                .sorted((uu1,uu2)->{return uu2.compareTo(uu1);})       // sorted() 排序 默认正序（通过 Comparator 排序 可以设置正序、或 倒序！），倒序！
                .limit(1)       // limit() 排序，只输出一个
                .forEach(System.out::println);      // forEach() 循环（使用了 Consumer 消费型接口），使用上面的结果进行打印

    }
}
