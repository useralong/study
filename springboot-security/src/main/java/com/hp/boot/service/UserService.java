package com.hp.boot.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hp.boot.entity.User;

public interface UserService {
	
	
	List<User> ListUser();
    List<User> Pagelimit(@Param("pageStr") Integer pageStr,@Param("limit") Integer limit);

}
