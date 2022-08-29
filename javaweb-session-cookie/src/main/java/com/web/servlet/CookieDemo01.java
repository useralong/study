package com.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Cookie中文数据传输
 * URLEncoder.encode("你好","UTF-8") 存的时候设置编码
 * URLDecoder.decode(cookie.getValue(),"UTF-8") 用的时候解码
 */
public class CookieDemo01 extends HttpServlet {

    //由于get 或者 post 只是请求实现的不同方式，可以互相调用，业务逻辑都一样
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //服务器告诉你，你来的时间；把这个时间封装成一个 “信件”；你下次来，我就知道你来了

        //解决中文乱码问题
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        //响应输出   PrintWriter是一个打印输出流
        PrintWriter out = resp.getWriter();
        //resp.getWriter().writer(),只能打印输出文本格式的（包括html标签），不可以打印对象
        //resp.getWriter().print(),不仅可以打印输出文本格式的（包括html标签），还可以将一个对象以默认的编码方式转换为二进制字节输出

        //Cookie 服务器段从客户端获取
        Cookie[] cookies = req.getCookies(); //请求里获取Cookie，返回数组，可能存在多个

        //判断Cookie是否存在
        if(cookies != null){
            //如果存在怎么办
            out.write("你上一次访问的时间是：");

            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                //获取cookie的名字，并判断
                if(cookie.getName().equals("name")){
                    //获取cookie的值，并解码
                    out.write(URLDecoder.decode(cookie.getValue(),"UTF-8"));
                }
            }
        }else {
            //响应的时候，打印这句话
            out.write("这是您第一次访问本站！");
        }

        //服务给客户端响应一个中文的cookie，并给中文设置编码比较安全一些
        Cookie cookie = new Cookie("name", URLEncoder.encode("你好","UTF-8")); // 这里Cookie只能存储String的键值对

        //设置cookie的有效时间为 1天
        cookie.setMaxAge(24*60*60);

        resp.addCookie(cookie); //response添加这个Cookie

        resp.setContentType("text/html;charset=utf-8"); //防止输出文本乱码

        //super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
