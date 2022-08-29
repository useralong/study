package com.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mp.mapper.UserMapper;
import com.mp.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class WrapperTest {

    // 继承了BaseMapper 所有的方法都来自自己的父类
    // 我们也可以编写自己的扩展方法！
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        // 查询name不为空的用户，并且邮箱不为空的用户，并且年龄大于20岁的
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
                .isNotNull("email")
                .ge("age",20); //ge 代表大于等于 >=20

        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    void test2(){
        // 查询名字为 Tom
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","Tom");
        User user = userMapper.selectOne(wrapper); //查询一个数据，出现多个使用List 或 Map
        System.out.println(user);
    }

    @Test
    void test3(){
        // 查询 年龄在 20-25 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("name",20,25); // 区间
        Integer count = userMapper.selectCount(wrapper); // 查询结果数量
        System.out.println(count);
    }
    //模糊查询
    @Test
    void test4(){
        // 模糊查询
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 左和右  %e%
        wrapper.notLike("name","e")      // 不包含 e 的
                .likeRight("email","t"); // t%
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }
    //排序
    @Test
    void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 通过id进行排序
        wrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }



}
