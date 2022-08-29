package com.it.controller;

import com.alibaba.fastjson.JSON;
import com.it.domain.Order;
import com.it.domain.Product;
import com.it.service.OrderService;
import com.it.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Slf4j 注解 加上后，可以在类里直接用log.info() log.debug()打印日志
 */
//@RequestMapping("order")
@Controller
@Slf4j
public class OrderController2 {

    @Autowired
    private OrderService orderService;

    /**
     * 新建一个ProductService 通过Feign组件将这个service绑定在nacos中对应的微服务
     * 实现调用ProductService就是在调用nacos中对应的微服务
     * Feign中集成了Ribbon的负载均衡，application.yml配置的Ribbon负载均衡可以照用
     */
    @Autowired
    private ProductService productService;

    /**
     * 商品信息查询，以json返回对象
     */
    //@RequestMapping("/prod/{pid}")
    @ResponseBody
    public Order product(@PathVariable("pid") Integer pid){
        log.info("接收到{}号商品的下单请求,接下来调用商品微服务查询此商品信息",pid);
        //不再使用restTemplate 使用Feign改造后的ProductService来调用Product服务
        Product product = productService.findById(pid);
        //模拟一次网络延时，休眠2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("查询到{}号商品，内容为{}", pid,JSON.toJSONString(product));
        //下单 创建订单
        Order order = new Order();
        //模拟下单用户
        order.setUid(1);
        order.setUsername("测试用户");
        //模拟下单数量
        order.setNumber(1);
        //商品信息
        order.setPid(pid);
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        //为了不产生太多垃圾数据,暂时不做订单保存
        //orderService.save(order);
        log.info("创建订单成功，订单信息为{}",JSON.toJSONString(order));
        return order;

    }

    /**
     * 正常情况下可以访问
     * 线程阻塞后高并发情况下就访问不了
     * 例如：访问404等问题
     * @return
     */
    @RequestMapping("/message")
    public String message() {
        return "高并发下的问题测试";
    }
}
