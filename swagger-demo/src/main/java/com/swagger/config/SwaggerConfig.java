package com.swagger.config;

import com.swagger.controller.HelloController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration // 代表配置类
@EnableSwagger2 // 开启Swagger2
public class SwaggerConfig {

    // 多个分组
    @Bean
    public Docket docketA(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }
    @Bean
    public Docket docketB(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }

    // 配置了 Swagger 的 Docket 的 Bean 实例
    @Bean
    public Docket docket(Environment environment){

        // 设置要显示的 Swagger 环境
        Profiles profiles = Profiles.of("dev","test");
        // 获取项目的环境，将之传到 .enable() 设置中
        // 通过environment.acceptsProfiles 判断当前环境是否处在自己设定的环境中，是返回true
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("allen")
                .enable(flag)  // 是否开启Swagger  false 则不能访问，
                .select()
                 // RequestHandlerSelectors 配置要扫描接口的方式；
                 // RequestHandlerSelectors.basePackage("目录") 包扫描，指定目录扫描
                 // RequestHandlerSelectors.any() 扫描全部
                 // RequestHandlerSelectors.none() 都不扫描
                 // RequestHandlerSelectors.withClassAnnotation(RestController.class) 扫描类上的注解，参数是一个注解的反射对象（扫描有这个注解的类）
                 // RequestHandlerSelectors.withMethodAnnotation(GetMapping.class) 扫描方法上的注解，参数是一个注解的反射对象（扫描有这个注解的方法）
                .apis(RequestHandlerSelectors.basePackage("com.swagger.controller")) // 扫描controller
                // paths() 过滤什么路径
//                .paths(PathSelectors.ant("/user/**")) // 只扫描 user 下的 path
                .build(); // build 工厂模式，创建什么东西
    }

    // 配置 Swagger 信息 apiInfo
    private ApiInfo apiInfo(){
        // 作者信息
        Contact DEFAULT_CONTACT = new Contact("Allen", "暂时没有", "暂时没有");

        return new ApiInfo(
                "Allen 的 Swagger API 文档",
                "这个作者有点酷",
                "v1.0",
                "urn:tos",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
