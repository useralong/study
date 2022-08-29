package com.it.controller;

import com.it.domain.Order;
import com.it.service.ProductService;
import com.it.service.impl.OrderServiceImpl5;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
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
public class OrderController5 {

    @Autowired
    private OrderServiceImpl5 orderServiceImpl5;

    /**
     * 商品信息查询，以json返回对象
     */
    //@RequestMapping("/prod/{pid}")
    @ResponseBody
    public Order product(@PathVariable("pid") Integer pid){
        return orderServiceImpl5.createOrder(pid);
    }
}