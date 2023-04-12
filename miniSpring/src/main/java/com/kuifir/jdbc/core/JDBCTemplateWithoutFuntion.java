package com.kuifir.jdbc.core;

import java.sql.*;

public abstract class JDBCTemplateWithoutFuntion {

    public Object query(String sql) {
        String url = "jdbc:mysql://localhost:3306/mybatis";
        String username = "root";
        String password = "123456";

        // 注册驱动
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (null == connection) {
                throw new RuntimeException("连接失败");
            }
            //调用返回数据处理方法，由程序员自行实现
            return doInStatement(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Object doInStatement(ResultSet resultSet) throws SQLException;
}
