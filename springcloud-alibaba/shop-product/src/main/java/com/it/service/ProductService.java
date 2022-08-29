package com.it.service;

import com.it.domain.Product;

public interface ProductService {

    /**
     * 根据商品id查询信息
     * @param pid
     * @return
     */
    Product findById(Integer pid);

    /**
     * 扣减库存
     * @param pid
     * @param number
     */
    void reduceInventory(Integer pid, Integer number);
}
