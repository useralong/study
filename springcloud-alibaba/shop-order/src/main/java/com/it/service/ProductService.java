//package com.it.service;
//
//import com.it.domain.Product;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// *@FeignClient 注解  用于指定nacos中的某个微服务(一般是服务提供者)
// * value 用于指定nacos中的某个微服务
// * fallback 用于指定当前Feign接口的容错类
// * fallbackFactory 用于指定当前Feign接口的容错工厂类
// *
// * fallback 和 fallbackFactory的使用只能二选一
// */
//@FeignClient(value = "service-product")
//public interface ProductService {
//
//    /**
//     * @FeignClient 注解的value +@RequestMapping注解的值，其实就是一个方法的请求地址，请求我们的商品服务中的查询方法
//     * 也就是说ProductService 通过 @FeignClient(value = "service-product")，绑定了service-product微服务
//     * ProductService中的接口通过 @RequestMapping("/product/find/{pid}") 的写法绑定了service-product微服务下提供的方法接口
//     * @param pid
//     * @return
//     */
//    @RequestMapping("/product/find/{pid}")
//    Product findById(@PathVariable("pid") Integer pid);
//
//    /**
//     * 扣减库存
//     * 我们使用 @FeignClient(value="微服务在注册中心的名称") 注解来进行远程调用时
//     * 方法中传递的参数较多时，需要给对应的参数加上 @RequestParam("传递参数名称")注解
//     * 不加是会有问题的...
//     * @param pid 需要扣减的商品id
//     * @param number 扣减数量
//     */
//    @RequestMapping("/product/reduceInventory")
//    void reduceInventory(@RequestParam("pid") Integer pid,@RequestParam("number") Integer number);
//}
