package com.kuifir.normal.annotations.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 有些框架要求一些额外信息来配合源代码共同工作，在使用这种框架时，注解特别有用。
 * 加入你想实现一套基本的ORM功能来自动化数据库表的创建，你便可以通过XML描述符文件来指定类名、类中的每一个成员，以及数据库映射信息。
 * 而如果使用注解，你可以将所有的信息都维护在单个源代码文件中。
 * 要实现此功能，你需要注解来定义数据库表名、字段信息，以及要映射到属性的SQL类型。
 * <p></p>
 * 以下是历史一个注解，它会让注解处理器创建一个数据库表：
 * 以下是表字段的注解:
 * {@link Constraints}
 * {@link SQLString}
 * {@link SQLInteger}
 *
 * @author kuifir
 * @date 2023/6/12 23:45
 */

// 只适用于类
// 在@Target注解中指定的每个ElementType（元素类型）都是一条约束，它告诉编译器注解只能被应用于该特定类型。
// 你可以指定一个单值的enum元素类型，也可以指定一个用逗号分隔的任意值组成的列表。
// 如果想将注解应用于所有的ElementType，则可以将@Target注解全部去掉，虽然这么做不太常见。
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    // 该注解可以通过name为处理器要创建的数据库指定表名。
    String name() default "";
}
