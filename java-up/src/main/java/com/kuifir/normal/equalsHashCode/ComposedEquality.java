package com.kuifir.normal.equalsHashCode;

import java.util.Objects;

/**
 * 当使用另一个类来组合新类时，{@link java.util.Objects#equals(Object, Object)}更是能大显身手：
 *
 * @author kuifir
 * @date 2023/7/2 22:49
 */
class Part {
    String ss;
    double dd;

    Part(String ss, double dd) {
        this.ss = ss;
        this.dd = dd;
    }

    @Override
    public boolean equals(Object rval) {
        return rval instanceof Part other
                && Objects.equals(ss, other.ss)
                && Objects.equals(dd, other.dd);
    }
}

public class ComposedEquality extends SuccinctEquality {

    Part part;

    public ComposedEquality(int i, String s, double d) {
        super(i, s, d);
        part = new Part(s, d);
        System.out.println("new 'ComposedEquality'");
    }

    @Override
    public boolean equals(Object rval) {
        return rval instanceof ComposedEquality other
                && super.equals(rval)
                && Objects.equals(part, other.part);
    }

    public static void main(String[] args) {
        testAll(ComposedEquality::new);
    }
}
