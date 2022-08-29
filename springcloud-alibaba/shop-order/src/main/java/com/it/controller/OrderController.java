package com.it.controller;

import com.alibaba.fastjson.JSON;
import com.it.domain.Order;
import com.it.domain.Product;
import com.it.service.OrderService;
import com.it.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Slf4j 注解 加上后，可以在类里直接用log.info() log.debug()打印日志
 */
@RequestMapping("order")
@Controller
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 这里直接引入 common公共服务中的 ProductService（每个服务消费者调用这个服务的时候就不用单独创建接口了）
     * @Reference 注解 通过dubbo引用服务（ProductService接口的实现类本身就是通过dubbo注解暴露了，并在服务提供者加了dubbo的配置了）
     */
    @Reference
    private ProductService productService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 商品信息查询，以json返回对象
     */
    @RequestMapping("/prod/{pid}")
    @ResponseBody
    public Order product(@PathVariable("pid") Integer pid){
        log.info("接收到{}号商品的下单请求,接下来调用商品微服务查询此商品信息",pid);

        //不再使用restTemplate 使用Feign改造后的ProductService来调用Product服务
        Product product = productService.findById(pid);
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
        orderService.save(order);
        log.info("创建订单成功，订单信息为{}",JSON.toJSONString(order));

        //下单成功之后,将消息放到mq中
        //参数一: 指定topic
        //参数二: 指定消息体
        rocketMQTemplate.convertAndSend("order-topic", order);

        return order;

    }
}
