package com.it.test;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 接收消息
 */
public class RocketMQReceiveTest {

    public static void main(String[] args) throws Exception {
        //1. 创建消息消费者, 指定消费者所属的组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("myconsumer-group");
        //2. 指定Nameserver地址
        consumer.setNamesrvAddr("120.25.199.215:9876");
        //3. 指定消费者订阅的主题和标签
        consumer.subscribe("myTopic", "*");
        //4. 设置回调函数，并在函数中编写收到消息后处理消息的方法
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //处理获取到的消息
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //消费逻辑
                System.out.println("Receive New Messages: " + list);
                //返回消费状态
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5. 启动消息消费者
        consumer.start();
        System.out.println("Consumer Started.");
    }
}
