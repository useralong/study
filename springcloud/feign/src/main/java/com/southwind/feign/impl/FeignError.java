package com.southwind.feign.impl;

import com.southwind.entity.Student;
import com.southwind.feign.FeignProviderClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * FeignError类实现 feign服务的声明式接口，
 * 将接口的抽象方法进行实现容错(熔断时，给出友好的提示，如index方法的提示)
 *
 * @Component 注解 将声明式接口的实现类进行容错的FeignError类 注入到IOC中
 */
@Component
public class FeignError implements FeignProviderClient {
    @Override
    public Collection<Student> findAll() {
        return null;
    }

    @Override
    public String index() {
        return "服务器维护中.....";
    }
}
