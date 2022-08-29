package com.southwind.feign;

import com.southwind.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

/**
 * @FeignClient(value = "xxx") 注解
 * 描述的是当前接口，访问的是在注册中心注册的哪个微服务；xxx 是在注册中心注册的服务名称
 * 当前接口想要访问注册中心注册的 provider 服务，所以写法如下：
 */
@FeignClient(value = "provider")
public interface FeignProviderClient {
    /**
     * 当前抽象方法需要访问对应 provider 服务的某个接口去实现
     * 则需要调用  @GetMapping("xxx") 注解 去访问对应的 get 接口
     * 注解中的xxx : 代表的是所调用的接口在服务内部的访问路径
     * 如当前象方法调用的 findAll 接口在注解里的路径就是在 provider 服务内部的路径
     * 如下所示：
     * @return
     */
    @GetMapping("/student/findAll")
    public Collection<Student> findAll();
    @GetMapping("/student/index")
    public String index();
}
