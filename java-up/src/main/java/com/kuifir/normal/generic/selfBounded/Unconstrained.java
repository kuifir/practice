package com.kuifir.normal.generic.selfBounded;

/**
 * 自限定执行了额外的一步，强制将泛型作为自己的边界参数使用。
 * 接下来看看这样写出的类能用来做什么，又不能做什么。{@link SelfBounded}
 *
 * @see SelfBounded
 * @see BasicHolder
 * @author kuifir
 * @date 2023/5/28 16:53
 */
public class Unconstrained {
    public static void main(String[] args) {
        BasicOther b = new BasicOther();
        BasicOther b2 = new BasicOther();
        b.set(new Other());
        Other other = b.get();
        b.f(); // Other
    }
}

class Other {
}

class BasicOther extends BasicHolder<Other> {
}