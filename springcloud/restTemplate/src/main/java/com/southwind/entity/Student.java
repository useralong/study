package com.southwind.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Data  主要是去掉get set方法
 * @AllArgsConstructor 全参构造函数
 * @NoArgsConstructor  为类提供无参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
}
