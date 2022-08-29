//package com.it.service.fallback;
//
//import com.it.domain.Product;
//import com.it.service.ProductService;
//import feign.hystrix.FallbackFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
///**
// * 这是一个容错类 需要实现Fegin所在的接口，并去实现接口中的所有方法
// * 一旦Feign远程调用出现问题了，就会进入当前类中同名方法，执行容错逻辑！
// *
// * 当前容错类，实现一个FallbackFactory<泛型>   泛型：就是需要容错的接口
// */
//@Component
//@Slf4j
//public class ProductServiceFallBackFactory implements FallbackFactory<ProductService> {
//
//    //Throwable 就是feign在调用过程中产生的异常
//    @Override
//    public ProductService create(Throwable throwable) {
//        return new ProductService() {
//            @Override
//            public Product findById(Integer pid) {
//                log.error("{}",throwable);
//                //容错逻辑
//                Product product = new Product();
//                product.setPid(-1);
//                product.setPname("远程调用商品微服务出现异常，进入容错逻辑!");
//                return product;
//            }
//        };
//    }
//}
