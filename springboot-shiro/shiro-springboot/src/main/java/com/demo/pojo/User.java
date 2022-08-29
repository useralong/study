package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * lombok注解
 * @Data 不用写get,set方法
 * @AllArgsConstructor 代表全参
 * @NoArgsConstructor 代表无参
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String name;
    private String pwd;
    /**
     * MD5盐值加密的随机数/也可直接用用户名替换
     */
    private int salt;
    /**
     * 账号的权限颗粒
     */
    private String perms;
}
