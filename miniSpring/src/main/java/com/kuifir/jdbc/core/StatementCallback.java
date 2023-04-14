package com.kuifir.jdbc.core;

import java.sql.Statement;
import java.util.function.Function;

public interface StatementCallback extends Function<Statement, Object> {
}
