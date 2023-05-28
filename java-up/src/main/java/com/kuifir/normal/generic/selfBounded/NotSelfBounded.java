package com.kuifir.normal.generic.selfBounded;

/**
 * @author kuifir
 * @date 2023/5/28 17:18
 * @see SelfBounded
 */
public class NotSelfBounded<T> {
    T element;

    NotSelfBounded<T> set(T arg) {
        element = arg;
        return this;
    }

    T get() {
        return element;
    }
}

class A2 extends NotSelfBounded<A2> {
}

class B2 extends NotSelfBounded<A2> {
}

class C2 extends NotSelfBounded<C2> {
    C2 setAndGet(C2 arg) {
        set(arg);
        return get();
    }
}
class D2{}

/**
 * 现在可以这样了
 */
class E2 extends NotSelfBounded<D2>{

}