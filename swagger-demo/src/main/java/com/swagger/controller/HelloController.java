package com.swagger.controller;

import com.swagger.pojo.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@Api("控制层")
@RestController
public class HelloController {

    // 使用 Swagger 后，由于需要生成的接口文档，需要精确到方法的类型
    // 所以 不要使用@RequestMapping注解映射，要精确到具体的方法类型去映射；
    // 如：@GetMapping @PostMapping @PutMapping @DeleteMapping

    @GetMapping(value = "/hello")
    public String hello(){
        return "hello";
    }

    // 只要我们的接口中，返回值中存在实体类，它就会被 Swagger 扫描

    @PostMapping(value = "/user")
    public User user(){
        return new User();
    }

    @ApiOperation("hello1接口") // 接口加上注释（不是放在类上的，是放在方法上的）
    @GetMapping("/hello1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true,dataType = "String",paramType = "")
    })
    public String hello1(@ApiParam(name = "username",value = "用户名") String username){
        return "hello"+username;
    }

}
