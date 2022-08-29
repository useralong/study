package com.demo.controller;

import com.demo.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MyController {

    @RequestMapping({"/","/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","hello,Shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    /**
     * 添加新用户并给密码设置 MD5盐值加密
     * @param user
     */
    public void addUser(User user) {
        //注意：Math.random()方法结果可能会是0，所以进行了 +1 操作
        int round = (int) Math.round((Math.random() + 1) * 1000);
        user.setSalt(round);
        // 两个参数，第一个是需要加密的字符串，第二个是盐
        Md5Hash hash = new Md5Hash(user.getPwd(), user.getName()+round);
        user.setPwd(hash.toString());
        //保存对象到数据库

    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username,String password,Model model){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据(放进令牌加密)
        UsernamePasswordToken tokn = new UsernamePasswordToken(username, password);
        try {
            //执行登录方法(subject当前用户使用登录方法进行校验登录用户密码加密后的tokn)没有异常说明登录成功
            //有异常则需要捕获一下
            subject.login(tokn);
            //认证成功则返回进入首页
            return "index";
        } catch (UnknownAccountException uae) {//用户名不存在异常时抛出
            model.addAttribute("msg","用户名不存在");
            //抛出异常则回到登陆页重新登录
            return "login";
        }catch (IncorrectCredentialsException ice) {//密码不对时抛出异常
            model.addAttribute("msg","密码错误");
            //抛出异常则回到登陆页重新登录
            return "login";
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg","安全退出！");
        return "login";
    }

    /**
     * 未授权页面方法
     * @return
     */
    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }



}
