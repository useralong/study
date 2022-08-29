package com.websocket.chat.controller;

import com.websocket.chat.pojo.Result;
import com.websocket.chat.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @RequestMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session){
        Result result = new Result();
        //只要密码为123的用户，不管什么用户名都能登录
        if(username != null && password != null && "123".equals(password)){
            result.setFlag(true);
            //登录成功后将用户名存储在session中
            session.setAttribute("user",username);
        }else {
            result.setFlag(false);
            result.setMessage("登录失败！");
        }


        return result;
    }

    @RequestMapping("/getUsername")
    public String getUsername(HttpSession session){
        String username = (String) session.getAttribute("user");
        return username;
    }
}
