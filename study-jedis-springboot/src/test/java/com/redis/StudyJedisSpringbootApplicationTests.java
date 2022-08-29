package com.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class StudyJedisSpringbootApplicationTests {

    @Resource
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

        // redisTemplate.opsFor***  调用都是 opsFor** 什么什么
        // redisTemplate.opsForValue()   opsForValue 是操作字符串的，类似String
        // redisTemplate.opsForList()    opsForList  是操作List的，类似List
        // 。。。。。。
        redisTemplate.opsForValue().set("myKey","myValue");
        System.out.println(redisTemplate.opsForValue().get("myKey"));

        // 获取redis连接
        // 通过 redisTemplate.getConnectionFactory().getConnection() 操作  flushDb  和  flushAll
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//        connection.flushAll();

    }

}
