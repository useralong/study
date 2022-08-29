package com.it.service.impl;

import com.it.dao.TxLogDao;
import com.it.domain.Order;
import com.it.domain.TxLog;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;


/**
 * @RocketMQTransactionListener(txProducerGroup = "tx_producer_group") 注解
 * txProducerGroup = "组名"   必须与发送半事务消息设置的组名一致
 */
@Service
@RocketMQTransactionListener(txProducerGroup = "tx_producer_group")
public class OrderServiceImpl4Listener implements RocketMQLocalTransactionListener {

    @Autowired
    private OrderServiceImpl4 orderServiceImpl4;

    @Autowired
    private TxLogDao txLogDao;

    /**
     * 半事务消息发送成功，消息发送方执行本地事务
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        //获取发送半事务消息生成的事务id
        String txId = (String) message.getHeaders().get("txId");
        try{
            //3.执行本地事务
            Order order = (Order) o;
            orderServiceImpl4.createOrder(txId,order);
            //4执行成功提交事务
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            e.printStackTrace();
            //4执行失败ROLLBACK
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 第5步骤：未收到第4步确认提交时，进行消息事务回查
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        //获取发送半事务消息生成的事务id
        String txId = (String) message.getHeaders().get("txId");
        TxLog txLog = txLogDao.findById(txId).get();
        if(txLog != null){
            //本地事务（订单创建）成功了
            return RocketMQLocalTransactionState.COMMIT;
        }else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }
}
