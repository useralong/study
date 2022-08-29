package com.it.service.impl;

import com.alibaba.fastjson.JSON;
import com.it.dao.OrderDao;
import com.it.domain.Order;
import com.it.domain.Product;
import com.it.service.ProductService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OrderServiceImpl5 {

    @Autowired
    private OrderDao orderDao;

    /**
     * 新建一个ProductService 通过Feign组件将这个service绑定在nacos中对应的微服务
     * 实现调用ProductService就是在调用nacos中对应的微服务
     * Feign中集成了Ribbon的负载均衡，application.yml配置的Ribbon负载均衡可以照用
     */
    @Autowired
    private ProductService productService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GlobalTransactional //开启全局事务
    public Order createOrder(Integer pid) {
        log.info("接收到{}号商品的下单请求,接下来调用商品微服务查询此商品信息",pid);
        //1.不再使用restTemplate 使用Feign改造后的ProductService来调用Product服务查询商品信息
        Product product = productService.findById(pid);
        log.info("查询到{}号商品，内容为{}", pid, JSON.toJSONString(product));

        //2.下单 创建订单
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
        orderDao.save(order);
        log.info("创建订单成功，订单信息为{}",JSON.toJSONString(order));

        //3. 订单创建成功后，调用商品微服务进行扣库存
       // productService.reduceInventory(pid, order.getNumber());

        //4.下单成功之后,将消息放到mq中
        //参数一: 指定topic
        //参数二: 指定消息体
        rocketMQTemplate.convertAndSend("order-topic", order);

        return order;
    }

}
