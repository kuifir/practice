package com.kuifir.normal.annotations.basicGrammar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 下面是一个用于跟踪某项目中用例的简单注解，程序员会给某个特定用例所需的所有方法或方法集都加上注解。
 * 项目经理可以通过计算以实现的用例数来了解项目的进度，维护项目的开发人员可以轻松找到需要更新的用例，或者在系统内调试业务规则。
 * <p></p>
 * 注意，id和 description与方法声明很相似。
 * 因为id会受到编译器的类型检查，所以可以放心地用它将跟踪数据库链接到用例文档和源代码。
 * description元素有个默认值，如果在方法被注解时未指定该元素的值，则注解处理器会使用该默认值。
 * <p></p>
 * 注解元素：
 * <ul>
 *     注解所允许的所有元素类型:
 *     <li>所有的基本类型(int,float,boolean等)</li>
 *     <li>{@link String}</li>
 *     <li>{@link Class}</li>
 *     <li>enum</li>
 *     <li>{@link java.lang.annotation.Annotation} (注解)</li>
 *     <li>以上任何类型的数组</li>
 * </ul>
 * 如果尝试使用任何其他类型，编译器都会报错。
 * 注意，任何包装类都是不允许使用的，但由于有自动装箱机制，因此这实际上并不会造成限制。
 * 注解也可以组偶为元素的类型，正如{@link com.kuifir.normal.annotations.database.SQLInteger}、
 * {@link com.kuifir.normal.annotations.database.SQLInteger}，内嵌注解是非常有用的。
 * <p></p>
 * 默认值的限制：
 * 编译器对元素的默认值要求非常苛刻。所有元素都需要有确定的值，这意味着元素要么有默认值，要么由使用该注解的类来设定值。
 * 还有另一个限制：不论是在源代码中声明时，还是在注解中定义默认值时，任何非基本类型元素都不能赋值为null.
 * 这导致很难让处理器去判断某个元素存在与否，因为所有元素在所有注解声明中都是有效存在的。
 * 但可以通过检查该元素是否为特殊值（如空字符串或负值）来绕过这个限制。
 * <pre>{@code
 *     @Target(ElementType.METHOD)
 *     @Retention(RetentionPlicy.RUNTIME)
 *     public @interface SimulatingNul{
 *         int id() default -1;
 *         String description() default "";
 *     }
 * }</pre>
 * 这是定义注解时的一个经典技巧。
 * <p></p>
 * 用例{@link PasswordUtils}
 * 注解处理器{@link UseCaseTracker}
 *
 * @author kuifir
 * @date 2023/6/12 22:00
 * @see PasswordUtils
 * @see UseCaseTracker
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    int id();

    String description() default "no description";
}
