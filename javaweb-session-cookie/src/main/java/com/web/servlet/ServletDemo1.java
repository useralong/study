package com.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ServletDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //处理请求
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String[] hobbys = req.getParameterValues("hobbys");
        System.out.println("=============================");
        System.out.println(username+password);
        System.out.println(hobbys);
        System.out.println("=============================");

        //通过请求转发
        //req.getRequestDispatcher("/index.jsp").forward(req,resp);
        req.getRequestDispatcher(req.getContextPath()+"/index.jsp").forward(req,resp);

        resp.setCharacterEncoding("utf-8");

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
