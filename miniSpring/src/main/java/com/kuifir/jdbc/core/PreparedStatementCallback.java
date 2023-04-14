package com.kuifir.jdbc.core;

import java.sql.PreparedStatement;
import java.util.function.Function;

public interface PreparedStatementCallback extends Function<PreparedStatement, Object> {
}
