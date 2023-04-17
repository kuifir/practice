package com.kuifir.batis;

import com.kuifir.jdbc.core.JDBCTemplate;
import com.kuifir.jdbc.core.PreparedStatementCallback;
import com.kuifir.jdbc.core.RowMapper;

public interface SqlSession {
    void setJdbcTemplate(JDBCTemplate jdbcTemplate);

    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    <T> T selectOne(String sqlid, Object[] args, RowMapper<T> rowMapper);
}
