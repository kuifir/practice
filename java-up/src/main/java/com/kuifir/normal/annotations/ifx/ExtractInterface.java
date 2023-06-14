package com.kuifir.normal.annotations.ifx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用javac 处理注解——更复杂的处理器
 * 当你创建了一个配合javac使用的注解处理器后，便无法使用Java的反射功能，
 * 因为此时操作的是源代码，而不是编译后的类。各种mirror（镜子。Java的设计者腼腆的提示，镜子就是指你发现反射的地方）
 * 可以解决该问题，方法是让你在未编译的源代码中查看方法、字段、类型。
 * <p></p>
 * 以下示例是一个注解，他从一个类中提取public方法，以将它们转换为接口。
 *
 * @author kuifir
 * @date 2023/6/14 22:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtractInterface {
    String interfaceName() default "-!!-";
}
