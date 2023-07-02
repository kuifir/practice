package com.kuifir.normal.equalsHashCode;

import java.util.Objects;

/**
 * 现在我们将定义一个Equality类，它包含了三个字段（在比较时我们认为所有这些字段都很重要），
 * 还有一个满足了上述四项检查的equals()方法。构造器会打印类型的名称，以确保程序执行的是我们想要的测试。
 *
 * @author kuifir
 * @date 2023/7/2 22:17
 */
public class Equality {
    protected int i;
    protected String s;
    protected double d;

    public Equality(int i, String s, double d) {
        this.i = i;
        this.s = s;
        this.d = d;
        System.out.println("made 'Equality'");
    }

    @Override
    public boolean equals(Object rval) {
        if (rval == null) {
            return false;
        }
        if (rval == this) {
            return true;
        }
        if (!(rval instanceof Equality other)) {
            return false;
        }
        if (!Objects.equals(i, other.i)) {
            return false;
        }
        if (!Objects.equals(s, other.s)) {
            return false;
        }
        return Objects.equals(d, other.d);
    }

    public void test(String descr, String expected, Object val) {
        System.out.format("""
                --Testing %s--
                Expected %s, got %s
                """, descr, expected, equals(val));
    }

    public static void testAll(EqualityFactory eqf) {
        Equality
                e = eqf.make(1, "Monty", 3.14),
                eq = eqf.make(1, "Monty", 3.14),
                neq = eqf.make(99, "Bob", 1.618);
        e.test("null", "false", null);
        e.test("same object", "true", e);
        e.test("different type", "false", 99);
        e.test("same values", "true", eq);
        e.test("different values", "false", neq);
    }

    public static void main(String[] args) {
        testAll((Equality::new));
    }
}
