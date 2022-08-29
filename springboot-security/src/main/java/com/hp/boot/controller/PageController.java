package com.hp.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("page")
public class PageController {



	/**
	 * 测试Spring security 路径
	 * @return
	 */
	@RequestMapping({"/","index"})
	public String home(){
		return "index";
	}

	@RequestMapping("level1/{id}")
	public String level1(@PathVariable("id") int id){
		System.out.println(id);
		return "view/level1/"+id;
	}

	@RequestMapping("level2/{id}")
	public String level2(@PathVariable("id") int id){
		return "view/level2/"+id;
	}

	@RequestMapping("toLogin")
	public String toLogin(){
		return "login";
	}

	/**
	 * userlist
	 */
	@RequestMapping("userlist")
	public String userlist() {
		return "admin/user/userlist";
	}

	/**
	 * 后台首页
	 * @return
	 */
	@RequestMapping("adminUI")
	public String adminUI() {
		return "admin/adminUI";

	}
	/**
	 * thymeleaf
	 */
	@RequestMapping("thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("key",111);
		return "home";

	}




}
