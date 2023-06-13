package com.kuifir.normal.annotations.simplest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用javac处理注解
 * 通过javac,你可以创建编译时注解处理器，并将注解应用于Java源文件，而不是编译后的类文件。
 * 不过这里有个重要的限制：无法通过注解处理器来修改源代码。唯一能影响结果的方法是创建新的文件。
 * 如果注解处理器创建了一个新的源文件，则在新一轮处理中会检查该文件自身的注解。
 * 该工具会一轮接着一轮地持续处理，直到不再有新的源文件被创建，然后就编译所有的源文件
 * <p></p>
 * 你编写的每个注解都需要自己的处理器，但是javac可以轻松地将若干注解处理器进行组合。
 * 你可以指定多个要处理的类，并且还可以添加监听器来接受一轮处理完成的通知。
 * <p></p>
 * {@link Retention} 现在成了SOURCE，这意味着该注解不会存活到编译后的代码中。
 * 对于编译期的注解操作，并不需要这么做——这只是为了表明此时javac是唯一有机会处理注解的代理。
 * 测试类{@link SimpleTest}
 *
 * @see SimpleTest
 * @author kuifir
 * @date 2023/6/13 23:24
 */
@Retention(RetentionPolicy.SOURCE)
@Target({
        ElementType.TYPE,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.ANNOTATION_TYPE,
        ElementType.PACKAGE,
        ElementType.FIELD,
        ElementType.LOCAL_VARIABLE,
})
public @interface Simple {
    String value() default "-default-";
}
