package com.kuifir.normal.equalsHashCode;

import java.util.Objects;

/**
 * 更简明的Equality
 * {@link Equality}的equals()方法冗长的令人生厌，可以简化成经典形式。我们可以看到：
 * 1. 使用了instanceof 进行检查之后，就不必再检查是否为null了；
 * 2. 与this进行比较是多余的。只要能正确编写equals()方法，自我比较肯定不会有问题。
 * && 操作符可以进行短路比较，它在第一次遇到失败时就会退出并返回false。
 * 因此，用&&操作符将这些检查连接起来，这样实现的equals()方法会更简洁。
 *
 * @author kuifir
 * @date 2023/7/2 22:36
 */
public class SuccinctEquality extends Equality {
    public SuccinctEquality(int i, String s, double d) {
        super(i, s, d);
        System.out.println("made 'SuccinctEquality'");
    }

    @Override
    public boolean equals(Object rval) {
        return rval instanceof SuccinctEquality other
                && Objects.equals(i, other.i)
                && Objects.equals(s, other.s)
                && Objects.equals(d, other.d);
    }

    public static void main(String[] args) {
        testAll(SuccinctEquality::new);
    }
}
