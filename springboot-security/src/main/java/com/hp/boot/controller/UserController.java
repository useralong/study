package com.hp.boot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.boot.entity.User;
import com.hp.boot.service.UserService;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	Map<String, Object> jsonData = new HashMap<String, Object>();



	//全查
	@RequestMapping("list")
	public Map<String, Object> List(Integer page,Integer limit){
		Integer pageStr = (page-1)*limit;
		List<User> list = userService.ListUser();
		List<User> list2 = userService.Pagelimit(pageStr, limit);
		
		jsonData.put("code", 0);
		jsonData.put("msg", "");
		jsonData.put("count", list.size());
		jsonData.put("data", list2);
		
		return jsonData;
	}
	
}
