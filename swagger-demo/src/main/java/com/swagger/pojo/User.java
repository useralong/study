package com.swagger.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//@Api("注释")
@ApiModel("用户实体类模块") // 用在实体类上
public class User {

    // @ApiModelProperty(hidden = true) // 忽略某个属性的注解

    @ApiModelProperty("用户名") // 用在实体类的字段上
    private String username;
    @ApiModelProperty(allowableValues = "密码") // 用在实体类的字段上
    private String password;

}
