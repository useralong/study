package com.mp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mp.mapper.UserMapper;
import com.mp.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusApplicationTests {

    // 继承了BaseMapper 所有的方法都来自自己的父类
    // 我们也可以编写自己的扩展方法！
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        // 参数是一个 Wrapper，条件构造器。不使用就赋值 null，代表查询全部
        // 查询全部用户
        List<User> users = userMapper.selectList(null);
        //users.forEach(user -> System.out.println(user));
        users.forEach(System.out::println);
    }

    //测试删除
    @Test
    public void testDeleteById(){
        int i = userMapper.deleteById(7L);

    }
    //测试批量删除
    @Test
    public void testDeleteBatchIds(){
        int i = userMapper.deleteBatchIds(Arrays.asList(1,2,3));

    }
    //测试条件删除
    @Test
    public void testDeleteByMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name","allen222");
        int i = userMapper.deleteByMap(map);

    }

    //测试分页查询
    @Test
    public void testPage(){
        // Page<>(当前页,页面大小);设置每页五条数据
        // 底层sql还是用的limit
        Page<User> page = new Page<>(1,5);// 一定要是mybatis-plus的Page
        IPage<User> userIPage = userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
        System.out.println(page.getSize());
    }

    //测试查询
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(7L);
        System.out.println(user);
    }
    //测试批量查询
    @Test
    public void testSelectByBatchIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }
    //条件查询 map
    @Test
    public void testSelectByMap(){
        Map<String, Object> map = new HashMap<>();
        //自定义要查询
        map.put("name","allen222");
        map.put("age",3);

        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试乐观锁成功！
    @Test
    public void testOptimisticLocker(){
        //1.查询用户信息
        User user = userMapper.selectById(1L);
        //2.修改用户信息
        user.setName("allen");
        user.setEmail("allen@email");
        //3.执行更新操作
        userMapper.updateById(user);

    }

    //测试乐观锁失败！
    @Test
    public void testOptimisticLocker2(){
        //线程1
        User user = userMapper.selectById(1L);
        user.setName("allen111");
        user.setEmail("allen111@email");
        //线程2
        User user2 = userMapper.selectById(1L);
        user2.setName("allen222");
        user2.setEmail("allen222@email");
        //先执行线程2（模拟插队）
        //模拟两个线程获取同一个资源，同样的version；突然有一个线程插队执行完，另一个线程执行失败的情况
        userMapper.updateById(user2);
        //因为乐观锁多以执行失败，可以用自旋锁来多次尝试提交
        userMapper.updateById(user); //到这里执行时，乐观锁 version 版本不是最新的了，如果没有乐观锁就会直接覆盖别人修改好的值

    }

    //测试更新
    @Test
    public void testUpdate(){
        User user = new User();
        // 通过条件自动拼接动态SQL
        user.setId(7L);
        user.setName("AllenW");
        user.setAge(19);
        // 注意： updateById 参数是我们对应的对象！
        int update = userMapper.updateById(user);
        System.out.println(update); // 受影响的行数

    }

    //测试插入
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("Allen@@");
        user.setAge(3);
        user.setEmail("A@email.com");

        int insert = userMapper.insert(user); //帮我们自动生成id
        System.out.println(insert); // 受影响的行数
        System.out.println(user); // 发现id会自动回填
    }

}
