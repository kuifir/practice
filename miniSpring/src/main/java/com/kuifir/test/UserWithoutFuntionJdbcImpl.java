package com.kuifir.test;

import com.kuifir.jdbc.core.JDBCTemplateWithoutFuntion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserWithoutFuntionJdbcImpl extends JDBCTemplateWithoutFuntion {

    protected Object doInStatement(ResultSet resultSet) {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setSex(resultSet.getInt("sex"));
                user.setAddress(resultSet.getString("address"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
