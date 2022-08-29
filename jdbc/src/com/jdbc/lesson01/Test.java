package com.jdbc.lesson01;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * 通过反射获取泛型
 */
public class Test{

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        Class c1 = Class.forName("com.jdbc.lesson01.Student");

        //通过反射获取注解
        Annotation[] annotations = c1.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

        //获取指定注解的value值
        TableAdd tableAdd = (TableAdd) c1.getAnnotation(TableAdd.class);
        String value = tableAdd.value();
        System.out.println(value);

        //获得类指定的注解
        Field f = c1.getDeclaredField("id");
        FieldAdd fieldAdd = f.getAnnotation(FieldAdd.class);
        System.out.println(fieldAdd.columnName());
        System.out.println(fieldAdd.type());
        System.out.println(fieldAdd.length());

    }


}

//实体类：pojo  entity
//使用我们自定义的注解
@TableAdd("db_student")
class Student{
    @FieldAdd(columnName = "db_id",type = "int",length = 10)
    private int id;
    @FieldAdd(columnName = "db_name",type = "varchar",length = 20)
    private String name;
    @FieldAdd(columnName = "db_age",type = "int",length = 3)
    private int age;

    public Student() {
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

//自定义类名注解 @TableAdd
// @Target 修饰注解，称为元注解；@Target(ElementType.TYPE) 设置我们自定义注解只能声明在一个类前。
// @Retention 修饰注解，称为元注解；@Retention(RetentionPolicy.RUNTIME) 设置我们自定义保存到class文件中，jvm加载class文件之后，仍然存在
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface TableAdd{
    String value();
}

//自定义属性注解 @FieldAdd
// @Target 修饰注解，称为元注解；@Target(ElementType.FIELD) 设置我们自定义注解只能声明在字段前。
// @Retention 修饰注解，称为元注解；@Retention(RetentionPolicy.RUNTIME) 设置我们自定义保存到class文件中，jvm加载class文件之后，仍然存在
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface FieldAdd{
    String columnName();
    String type();
    int length();
}
