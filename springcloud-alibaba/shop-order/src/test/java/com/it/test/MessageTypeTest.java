package com.it.test;

import com.it.OrderApplication;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//测试
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class MessageTypeTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 同步消息
     */
    @Test
    public void testSyncSend() {
        //参数一: topic， 如果想添加tag 可以使用"topic:tag"的写法
        //参数二: 消息内容
        //参数二: 超时时间
        SendResult sendResult = rocketMQTemplate.syncSend("test-topic-1", "这是一条同步消息",10000);
        System.out.println(sendResult);
    }

    /**
     * 异步消息（可用于上传时间过长的文件等消息）
     */
    @Test
    public void testAsyncSend() throws InterruptedException {
        //参数一: topic, 如果想添加tag 可以使用"topic:tag"的写法
        //参数二: 消息内容
        //参数三: 回调函数, 处理返回结果
        rocketMQTemplate.asyncSend("test-topic-1", "这是一条异步消息", new SendCallback() {
            //成功响应回调
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }
            //异常响应回调
            @Override
            public void onException(Throwable throwable) {
                System.out.println(throwable);
            }
        });
        System.out.println("************************");
        //让线程不要终止(保证测试异步 SendCallback()回调的时候，方法不会结束能看到回调信息)
        Thread.sleep(30000000);

    }

    /**
     * 单向消息（可用于日志收集）
     */
    @Test
    public void testOneWay() {
        rocketMQTemplate.sendOneWay("test-topic-1", "这是一条单向消息");
    }

}
