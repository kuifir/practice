package com.kuifir.normal.annotations.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Constraints} 注解使得处理器可以提取出数据库表的元数据，
 * 这相当于数据库提供的一个小的约束子集，不过它可以帮助你形成一个整体的概念。
 * 通过primaryKey()、allowNull()、unique()元素设置合理的默认值，可以帮使用者减少很多编码工作，
 * @author kuifir
 * @date 2023/6/13 0:01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
    boolean primaryKey() default false;

    boolean allowNull() default false;

    boolean unique() default false;
}
