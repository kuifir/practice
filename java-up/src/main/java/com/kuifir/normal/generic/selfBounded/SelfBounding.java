package com.kuifir.normal.generic.selfBounded;

/**
 * 有一种相当"烧脑"的早期Java泛型习惯用法，它看起来像下面这样
 * <pre>{@code
 * class SelfBounded<T extend SelfBounded<T>>{
 *  // ...
 * }
 * }</pre>
 * 这就好比将两面镜子彼此相对，有种无限镜像反射的效果。
 * SelfBounded类将泛型参数T作为参数，T受边界所限制，而边界又是带着参数T的SelfBounded。
 * 这种方式乍一看很难理解，这也再次强调了extends关键字在用于边界时的意义，和它在用于创建子类是的意义是完全不同的。
 * <p></p>
 * 要理解自限定类型是什么意思，我们先来看一个该用法的简化版本，它并未使用自限定。{@link CuriouslyRecurringGeneric}
 * <p></p>
 * 继Jim Coplien 在C++领域提出奇异递归模板模式(Curiously Recurring Template Pattern)后,
 * 这种方式可以称为奇异递归泛型(curiously recurring generics,CGR)。
 * 其中，“奇异递归”指的是你的类奇怪地在自身的基类中出现的现象。
 * 要理解这其中的意义，你可以试着大声说：“我要创建一个新类，它继承自将该新类类名作为自身参数的泛型类型。”
 * 泛型基类在拿到子类类名后，能做些什么呢？
 * Java泛型的重点在于参数和返回类型，因此可以生成将派生类型作为参数和返回值的基类。
 * 也可以将派生类型作为字段的类型，尽管它们被擦除为Object。下面这个泛型类诠释了该模式{@link com.kuifir.normal.generic.selfBounded.BasicHolder}
 * <p></p>
 * 自限定要求类处于继承关系中：
 * <pre>{@code
 *  class A extends SelfBounded<A>{}
 * }</pre>
 * 这会强制要求你必须将你要定义的类作为参数传给基类。
 * <p></p>
 * 对参数进行自限定，这带来了什么额外的价值呢?
 * 类型参数必须和要定义的类是同一种类型。
 * 这正如你在{@link B}的定义中所看到的，你也可以从使用了另一个SelfBounded参数的SelfBounded派生出类，
 * 尽管你看到的主要用法似乎适用于类A的。从试图定义E的那一行可以看出，你无法将非SelfBounded的类型作为类型参数。
 * 遗憾的是，编译器并未对F进行警告，因此自限定的用法并不是强制执行的。
 * 如果它真的很重要，它可以引入并通过拓展工具来确保 未使用 原生类型 来代替 参数化类型。
 * <p></p>
 * 注意，你可以一处该限制，所有的类依然可以编译，但是E也可以编译了{@link NotSelfBounded}
 * 很明显，自限定的限制只服务于强制继承关系。
 * 如果你使用自限定，你会知道该类使用的类型参数和使用该参数的类是同一基类。他强制任何使用该类的人都遵从这种形式。
 * <p></p>
 * 也可以将自限定用于泛型方法：{@link SelfBoundingMethod}
 * 这使得该方法无法应用于除所示形式的 自限定参数 外的任何对象
 * @author kuifir
 * @date 2023/5/28 15:13
 * @see BasicHolder
 * @see CuriouslyRecurringGeneric
 * @see com.kuifir.normal.generic.selfBounded.BasicHolder
 */
class SelfBounded<T extends SelfBounded<T>> {
    T element;

    SelfBounded<T> set(T arg) {
        element = arg;
        return this;
    }

    T get() {
        return element;
    }
}

class A extends SelfBounded<A> {
}

/**
 * 这样也可以
 */
class B extends SelfBounded<A> {
}

class C extends SelfBounded<C> {
    C setAndGet(C arg) {
        set(arg);
        return get();
    }
}

class D {
}
/**
 * 不能这样做:
 * Type parameter 'D' is not within its bound; should extend 'SelfBounded<D>'
 * class E extends SelfBounded<D>{
 * <p>
 * }
 * class E extends SelfBounded<D>{
 * <p>
 * }
 * class E extends SelfBounded<D>{
 * <p>
 * }
 */
/** class E extends SelfBounded<D>{

 }*/

/**
 * 你可以这样做，所以你无法强制使用这种方法
 */
class F extends SelfBounded {

}

/**
 *
 * @author kuifir
 */
public class SelfBounding {
    public static void main(String[] args) {
        A a = new A();
        a.set(new A());
        a = a.set(new A()).get();
        a = a.get();
        C c = new C();
        c = c.setAndGet(new C());
    }
}

