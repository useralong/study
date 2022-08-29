package com.it.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.it.service.impl.OrderServiceImpl3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Slf4j 注解 加上后，可以在类里直接用log.info() log.debug()打印日志
 */
//@RequestMapping("order")
@Controller
@Slf4j
public class OrderController3 {

    @Autowired
    private OrderServiceImpl3 orderServiceImpl3;

    @RequestMapping("/message1")
    @ResponseBody
    public String message1() {
       // orderServiceImpl3.message();
        return "message1";
    }
    //int i = 0;
    @RequestMapping("/message2")
    @ResponseBody
    public String message2() {
        //i++;
        //异常比例为0.333
//        if (i % 3 == 0){
//            throw new RuntimeException();
//        }
        //orderServiceImpl3.message();
        return "message2";
    }

    /**
     *  @SentinelResource("message3") 定义一个message3的资源
     *  注意这里必须使用这个注解标识,热点规则不生效
     * @return
     */
    @RequestMapping("/message3")
    @ResponseBody
    @SentinelResource("message3")
    public String message3(String name, Integer age) {
        return name+age;
    }

    /**
     * 调用service的message方法
     * @return
     */
    //@RequestMapping("/message")
    @ResponseBody
    public String message() {
        return orderServiceImpl3.message("xx");
    }
}
