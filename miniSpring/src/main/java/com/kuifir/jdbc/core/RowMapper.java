package com.kuifir.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 把 JDBC 返回的 ResultSet 里的某一行数据映射成一个对象。
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
