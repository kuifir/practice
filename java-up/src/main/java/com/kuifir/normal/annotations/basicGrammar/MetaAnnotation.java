package com.kuifir.normal.annotations.basicGrammar;

import java.lang.annotation.*;

/**
 * Java语言目前只定义了5个标准注解和5个元注解，元注解是为了对直接进行注解
 *
 * <ul>
 *     五个标准注解:
 *     <li>{@link java.lang.Override}：since Java 5 </li>
 *     <li>{@link java.lang.Deprecated}: since Java 5</li>
 *     <li>{@link java.lang.SuppressWarnings}: since Java 5</li>
 *     <li>{@link java.lang.SafeVarargs}: since Java 7,用于在使用泛型作为可变参数的方法或构造器中关闭对调用者的警告</li>
 *     <li>{@link java.lang.FunctionalInterface}: since Java 8</li>
 * </ul>
 * <table>
 *     <th>注解</th> <th>效果</th>
 *     <tr>
 *         <td>{@link Target}</td>
 *         <td>该注解可用的地方。可能的ElementType参数包括{@link java.lang.annotation.ElementType}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link Retention}</td>
 *         <td>
 *         注解信息可以保存多久。可能的{@link RetentionPolicy}参数包括：
 *             1. SOURCE ——注解会被编译器丢弃
 *             2. Class ——注解会在类文件中可被编译器使用，但会被虚拟机丢弃
 *             3. Runtime ——注解在运行时仍被虚拟机保留，因此可以通过反射读取到注解信息。
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@link Documented}</td>
 *         <td>在Javadoc中引入该注解</td>
 *     </tr>
 *     <tr>
 *         <td>{@link Inherited}</td>
 *         <td>允许子类继承父注解</td>
 *     </tr>
 *     <tr>
 *         <td>{@link Repeatable}</td>
 *         <td>可以多次应用于同一个声明（since Java 8）</td>
 *     </tr>
 * </table>
 * 大多数时候，你可以定义自己的注解，然后自定编写处理器来处理他们。
 *
 * @author kuifir
 * @date 2023/6/12 22:20
 * @see java.lang.Override
 * @see java.lang.Deprecated
 * @see java.lang.SuppressWarnings
 * @see java.lang.SafeVarargs
 * @see java.lang.FunctionalInterface
 */
public class MetaAnnotation {
}
