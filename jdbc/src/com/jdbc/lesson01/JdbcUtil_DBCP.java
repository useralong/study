package com.jdbc.lesson01;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtil_DBCP {

    private static BasicDataSource dataSource = null;

    static {
        try{
            // 通过类加载器，获取资源文件并转换成流
            InputStream in = JdbcUtil_DBCP.class.getClassLoader().getResourceAsStream("dpcpconfig.properties");
            Properties properties = new Properties();
            properties.load(in); // properties 加载流（properties文件转换成的流）

            // 创建数据源  工厂模式 -- > 创建
            dataSource = BasicDataSourceFactory.createDataSource(properties);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // 从数据源中获取连接
    }

    // 释放连接资源
    public static void release(Connection connection, Statement statement, ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
