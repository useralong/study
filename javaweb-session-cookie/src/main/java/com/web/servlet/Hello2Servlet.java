package com.web.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Hello2Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = this.getServletContext();
        //取出 servletContext 中的数据
        //可以在不同的servlet中操作（在HelloServlet使用 servletContext 存数据，在Hello2Servlet 使用 servletContext 取数据）
        String username1 = (String) servletContext.getAttribute("username");
        //防止响应乱码
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        resp.getWriter().write("名字："+username1);


        //super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
