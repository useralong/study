package com.jdbc.lesson01;


import java.sql.*;

/**
 * JDBC操作事务
 */
public class JdbcFirstDemo {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil_C3P0.getConnection(); // 调用工具类，获取数据库连接

            //查询
            // sql 使用 ? 占位符代替参数
            String sql = "select * from users where id = ?";
            preparedStatement = connection.prepareStatement(sql); // 通过数据库连接对象，加入sql进行预编译，获取sql执行对象

            // 手动给 占位符代替的参数赋值 (查询sql的赋值代码)
            preparedStatement.setInt(1,1);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println("查询成功！");
                System.out.println("NAME:"+resultSet.getString("NAME"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtil.release(connection,preparedStatement,resultSet);
        }
    }


}
