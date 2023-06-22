package com.kuifir.normal.annotations.atunit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义标签
 * 注解的定义也要求必须有<b>元注解</b>
 * {@link java.lang.annotation.Target}
 * {@link java.lang.annotation.Retention}.
 * {@link Target }定义了你可以在何处应用该注解（例如方法或字段）。
 * {@link Retention}定义了该注解在源代码（SOURCE）、类文件(CLASS)、或运行时(RUNTIME)中是否可用。
 * <p></p>
 * 注解通常包含一些可以设定值的元素。程序或者工具在处理注解时可以使用这些参数。元素看起来比较像接口方法，只不过你可以为其指定默认值。
 * <p></p>
 * 没有任何元素的注解称为<b>标记注解</b>
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Retention
 * @author kuifir
 * @date 2023/6/12 21:49
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
}
