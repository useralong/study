package com.web.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //this.getInitParameter();  初始化参数
        //this.getServletConfig();  serlvet配置(就是web.xml中的servlet配置)
        //this.getServletContext(); servlet上下文
        ServletContext servletContext = this.getServletContext();
        String username = "你好"; // 数据
        servletContext.setAttribute("username",username); //保存一个数据在servletContext中

        //取出 servletContext 中的数据
        //可以在不同的servlet中操作（在HelloServlet使用 servletContext 存数据，在Hello2Servlet 使用 servletContext 取数据）
        String username1 = (String) servletContext.getAttribute("username");

        resp.getWriter().write("名字："+username1);


        //super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
