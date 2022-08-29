package com.it.service.impl;

import com.it.dao.OrderDao;
import com.it.dao.TxLogDao;
import com.it.domain.Order;
import com.it.domain.TxLog;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl4 {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private TxLogDao txLogDao;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 作为消息发送放，发送半事务消息
     */
    public void createOrderBefore(Order order) {
        String txId = UUID.randomUUID().toString();
        //1.发送半事务消息
        //参数一 组名
        //参数二 主题名
        //参数三 消息体
        //参数四 参数
        rocketMQTemplate.sendMessageInTransaction(
                "tx_producer_group",
                "tx_topic",
                MessageBuilder.withPayload(order)
                        .setHeader("txId", txId)
                        .build(),
                order
        );
        //2.半事务消息发送成功
    }


    /**
     * 创建订单（本地事物）
     * @Transactional 事务注解，持久化时使用
     * @param txId
     * @param order
     */
    @Transactional
    public void createOrder(String txId, Order order) {
        //本地事物代码保存订单
        orderDao.save(order);
        //记录日志到数据库,回查使用
        TxLog txLog = new TxLog();
        txLog.setTxLogId(txId);
        txLog.setContent("事物测试");
        txLog.setDate(new Date());
        txLogDao.save(txLog);
    }



}
