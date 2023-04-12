package com.kuifir.jdbc.core;

import java.sql.*;
import java.util.function.Function;

public class JDBCTemplate {
    String url = "jdbc:mysql://localhost:3306/mybatis";
    String username = "root";
    String password = "123456";
    public Object query(Function<Statement, Object> function) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // 注册驱动
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
        ) {
            if (null == connection) {
                throw new RuntimeException("连接失败");
            }
            //调用返回数据处理方法，由程序员自行实现
            return function.apply(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object query(String sql, Object[] args, Function<PreparedStatement, Object> function) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(sql);
        ){
            for (int i = 0; i < args.length; i++) { //设置参数
                Object arg = args[i];
                //按照不同的数据类型调用JDBC的不同设置方法
                if (arg instanceof String) {
                    pstmt.setString(i+1, (String)arg);
                } else if (arg instanceof Integer) {
                    pstmt.setInt(i+1, (int)arg);
                }
            }
            return function.apply(pstmt);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
