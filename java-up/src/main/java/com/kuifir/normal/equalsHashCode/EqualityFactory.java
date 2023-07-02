package com.kuifir.normal.equalsHashCode;

/**
 * 下面的示例对比同版本的{@link Equality}类进行了比较。为了避免代码冗余，我们将使用
 * 工厂方法设计模式来构建示例。EqualityFactory接口提供了make()方法来创建Equality对象，
 * 这样不同的EqualityFactory就可以创建不同的Equality子类型了。
 *
 * @author kuifir
 * @date 2023/7/2 22:12
 */
public interface EqualityFactory {
    Equality make(int i, String s, double d);
}
