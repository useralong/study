package com.it.service;

import com.alibaba.fastjson.JSON;
import com.it.dao.UserDao;
import com.it.domain.Order;
import com.it.domain.User;
import com.it.utils.SmsUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 接收信息，发送短信的服务
 * SmsService 实现 RocketMQListener<T>接口
 * 泛型选我们接收消息的类型或实体
 * @Service  将当前的SmsService服务注入spring容器中，因为这里是service层，所以最好用@Service注解
 * @RocketMQMessageListener(consumerGroup = "shop-user", topic = "order-topic")
 * 解释：
 * @RocketMQMessageListener 注解；作用RocketMQ消息接收的服务类上，用于指定接收消息的组名和主题名
 * consumerGroup = "shop-user" 接收消息的组名
 * topic = "order-topic" 接收消息的主题
 * consumeMode = ConsumeMode.CONCURRENTLY  //消费者模式，指定是否按顺序消费 .CONCURRENTLY（同步，默认）.ORDERLY (按顺序)
 * messageModel = MessageModel.BROADCASTING //消息模式，指定消息的模式  .BROADCASTING（广播模式）.CLUSTERING（集群模式，默认）
 * 这里的SmsService名称与alibaba内部的服务名称冲突，启动会报错；
 * 需要使用@Service("shopSmsService") 注解重新指定一下当前service的名称
 */
@Slf4j
@Service("shopSmsService")
@RocketMQMessageListener(
        consumerGroup = "shop-user",
        topic = "order-topic",
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING
)
public class SmsService implements RocketMQListener<Order> {

    @Autowired
    private UserDao userDao;

    @Override
    public void onMessage(Order order) {
        //打印消息接收过来的内容，这里是打印接收过来的order
        log.info("收到一个订单信息{},接下来发送短信", JSON.toJSONString(order));

        //根据uid 获取手机号
        User user = userDao.findById(order.getUid()).get();
        //生成验证码(验证码要求第一个值不能为0)6位数验证码，第一个数字不为0
        StringBuilder stringBuilder = new StringBuilder();
        //循环6次，每次生成一个不为0的随机数
        for(int i=0; i < 6; i++){
            stringBuilder.append(new Random().nextInt(9)+1);
        }
        String smsCode = stringBuilder.toString();
        Param param = new Param(smsCode);

        try {
            //发送短信
            //参数1：手机号
            //参数2：阿里云短信签名名称
            //参数3：阿里云短信模板CODE
            //参数4：验证码（是json格式）{"code":"123456"}
            SmsUtil.sendSms(user.getTelephone(),"黑马旅游网","SMS_170836451",JSON.toJSONString(param));
            log.info("短信发送成功");
        }catch (Exception e){
            e.printStackTrace();
            log.info("短信发送失败{}",e);
        }


    }

    /**
     * 创建一个内部类，用于验证码的格式转化
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Param {
        private String code;
    }
}
