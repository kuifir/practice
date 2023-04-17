package com.kuifir.jdbc.core;

import com.kuifir.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class JDBCTemplate {


    @Autowired
    private DataSource dataSource;

    public Object query(StatementCallback statementCallback) {


        // 注册驱动
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
        ) {
            if (null == connection) {
                throw new RuntimeException("连接失败");
            }
            //调用返回数据处理方法，由程序员自行实现
            return statementCallback.apply(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object query(String sql, Object[] args, PreparedStatementCallback preparedStatementCallback) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
        ) {
            //通过argumentSetter统一设置参数值
            ArgumentPreparedStatementSetter argumentSetter
                    = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);
            return preparedStatementCallback.apply(pstmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public <T> List<T> queryList(String sql, Object[] args, RowMapper<T> rowMapper) {
        RowMapperResultSetExtractor<T> resultExtractor = new RowMapperResultSetExtractor<>(rowMapper);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
        ) {
            //通过argumentSetter统一设置参数值
            ArgumentPreparedStatementSetter argumentSetter
                    = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);
            //执行语句
            ResultSet resultSet = pstmt.executeQuery();
            return resultExtractor.extractData(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public <T> T queryOne(String sql, Object[] args, RowMapper<T> rowMapper) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
        ) {
            //通过argumentSetter统一设置参数值
            ArgumentPreparedStatementSetter argumentSetter
                    = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);
            //执行语句
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                return rowMapper.mapRow(resultSet,1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
