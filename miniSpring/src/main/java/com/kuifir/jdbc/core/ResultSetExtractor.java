package com.kuifir.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 把 JDBC 返回的 ResultSet 数据集映射为一个集合对象。
 */
public interface ResultSetExtractor<T> {
    T extractData(ResultSet rs) throws SQLException;
}
