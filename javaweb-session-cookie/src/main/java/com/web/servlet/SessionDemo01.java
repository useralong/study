package com.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionDemo01 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决乱码问题
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8"); //设置浏览器响应格式，防止输出文本乱码

        //得到session
        HttpSession session = req.getSession();

        //给session中存东西
        session.setAttribute("name","你好");

        //取session里的东西
        String name = (String) session.getAttribute("name");

        //获取sessionID
        String sessionId = session.getId();

        //判断session是不是新的
        if(session.isNew()){
            resp.getWriter().write("session创建成功,ID："+sessionId);
        }else {
            resp.getWriter().write(name+"session已经存在了！ID："+sessionId);
        }

        //session创建的时候做了什么事情？把SessionID放到了Cookie的JSESSIONID键中，并且把这个cookie响应回去了

        //super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
