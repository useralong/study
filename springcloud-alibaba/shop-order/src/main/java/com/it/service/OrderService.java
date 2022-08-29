package com.it.service;

import com.it.domain.Order;

public interface OrderService {


    /**
     * 创建保存订单信息
     * @param order
     */
    void save(Order order);
}
