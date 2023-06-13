package com.kuifir.normal.annotations.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义SQL类型。同样为了使该框架更好用，可以为每个额外的SQL类型都定义一个注解。
 * 这些类型都有一个name()元素和一个constraints()元素，
 * 后者利用<b>嵌套注解</b>的特性嵌入字段类型的数据库约束信息。
 * 注意constraints()元素默认值是@Constraints.
 * 该注解类型后面的圆括号中没有指定元素值，因此constraints()的默认值实际上是一个自身带有一套默认值的@Constraints注解。
 * 如果想内嵌@Constraints注解的唯一性默认值设置为true,可以像下面定义它的元素<pre>{@code
 * public @interface Uniqueness{
 *     Constraints constraints() default @Constraints(unique = true);
 * }
 * }</pre>
 *
 * @author kuifir
 * @date 2023/6/13 0:06
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
    String name() default "";
    Constraints constraints() default @Constraints;
}
