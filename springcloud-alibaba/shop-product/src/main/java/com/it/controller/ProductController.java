package com.it.controller;

import com.alibaba.fastjson.JSON;
import com.it.domain.Product;
import com.it.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Slf4j 注解 加上后，可以在类里直接用log.info() log.debug()打印日志
 */
@RequestMapping("product")
@Controller
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;


    @RequestMapping("/api1/demo1")
    @ResponseBody
    public String demo1(){
        return "domo1";
    }

    @RequestMapping("/api1/demo2")
    @ResponseBody
    public String demo2(){
        return "domo1";
    }

    @RequestMapping("/api2/demo1")
    @ResponseBody
    public String demo3(){
        return "domo3";
    }

    @RequestMapping("/api2/demo2")
    @ResponseBody
    public String demo4(){
        return "domo4";
    }

    /**
     * 商品信息查询
     * @param pid
     * @return
     */
    @RequestMapping("/find/{pid}")
    @ResponseBody
    public Product product(@PathVariable("pid") Integer pid) {
        log.info("接下来要进行{}号商品信息的查询", pid);
        Product product = productService.findById(pid);
        log.info("商品信息查询成功,内容为{}", JSON.toJSONString(product));
        return product;
    }

    /**
     * 商品微服务扣减库存
     * @param pid
     * @param number
     */
    @RequestMapping("/reduceInventory")
    @ResponseBody
    public void reduceInventory(Integer pid, Integer number) {
        productService.reduceInventory(pid, number);
    }
}
