package com.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mp.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * 在对应的 Mapper 上继承基本的类 BaseMapper<泛型用我们对应的实体类即可>
 *     @Repository 表示持久层
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    // 所有的CRUD操作已经编写完成了
}
