package com.it.service.impl;

import com.it.dao.ProductDao;
import com.it.domain.Product;
import com.it.service.ProductService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

//@Service
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product findById(Integer pid) {
        Optional<Product> product = productDao.findById(pid);
        if(product.isPresent()){
            Product one = product.get();
            return one;
        }else{
            return null;
        }
    }

    @Override
    public void reduceInventory(Integer pid, Integer number) {
        //根据商品id查询商品
        Product product = productDao.findById(pid).get();
        //校验库存是否充足
        if (product.getStock() < number) { throw new RuntimeException("库存不足"); }
        //直接内存中扣减库存
        product.setStock(product.getStock() - number);
        //模拟异常
        int i = 1 / 0;
        //保存最终操作后的商品对象
        productDao.save(product);
    }

}
