package com.kuifir.batis;

import com.kuifir.jdbc.core.JDBCTemplate;
import com.kuifir.jdbc.core.RowMapper;

public class DefaultSqlSession implements SqlSession {
    private JDBCTemplate jdbcTemplate;
    private SqlSessionFactory sqlSessionFactory;

    public JDBCTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public <T> T selectOne(String sqlid, Object[] args, RowMapper<T> rowMapper) {
        String sql = this.sqlSessionFactory.getMapperNode(sqlid).getSql();
        return jdbcTemplate.queryOne(sql, args, rowMapper);
    }

    private void buildParameter() {
    }

    private Object resultSet2Obj() {
        return null;
    }

    @Override
    public void setJdbcTemplate(JDBCTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }


}
