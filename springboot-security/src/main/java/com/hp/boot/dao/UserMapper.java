package com.hp.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hp.boot.entity.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer uId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> ListUser();
    List<User> Pagelimit(@Param("pageStr") Integer pageStr,@Param("limit") Integer limit);
}