package com.kuifir.normal.generic;

import java.util.Collection;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 使用Suppliers的实用工具
 * 填充动作的各种变种操作。
 *
 * @author kuifir
 * @date 2023/5/24 22:50
 */
public class Suppliers {
    /**
     * 创建并填充一个集合
     */
    public static <T, C extends Collection<T>> C create(Supplier<C> factory, Supplier<T> gen, int n) {
        return Stream.generate(gen)
                .limit(n)
                .collect(factory, C::add, C::addAll);
    }

    /**
     * 填充已有集合
     */
    public static <T, C extends Collection<T>> C fill(C coll, Supplier<T> gen, int n) {
        Stream.generate(gen)
                .limit(n)
                .forEach(coll::add);
        return coll;
    }

    /**
     * 使用未绑定的方法引用生成更为通用的方法
     */
    public static <H, A> H fill(H holder,
                                BiConsumer<H, A> adder,
                                Supplier<A> gen,
                                int n) {
        Stream.generate(gen)
                .limit(n)
                .forEach(a -> adder.accept(holder, a));
        return holder;
    }

}
