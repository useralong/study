package com.hp.boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.boot.dao.UserMapper;
import com.hp.boot.entity.User;
import com.hp.boot.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper UserDao;

	@Override
	public List<User> ListUser() {
		// TODO Auto-generated method stub
		return UserDao.ListUser();
	}

	@Override
	public List<User> Pagelimit(Integer pageStr, Integer limit) {
		// TODO Auto-generated method stub
		return UserDao.Pagelimit(pageStr, limit);
	}

}
