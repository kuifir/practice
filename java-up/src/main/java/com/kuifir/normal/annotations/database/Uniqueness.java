package com.kuifir.normal.annotations.database;

/**
 * @author kuifir
 * @date 2023/6/13 0:20
 */
public @interface Uniqueness {
    Constraints constrains() default @Constraints(unique = true);
}
