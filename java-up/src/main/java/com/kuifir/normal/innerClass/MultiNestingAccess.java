package com.kuifir.normal.innerClass;

/**
 * 一个内部类被嵌套多少层并不重要，它可以透明地访问包含他的所有类的所有成员
 * @author kuifir
 * @date 2023/5/7 16:38
 */
public class MultiNestingAccess {
    public static void main(String[] args) {
        MNA mna = new MNA();
        MNA.A mnaa = mna.new A();
        MNA.A.B mnaab = mnaa.new B();
        mnaab.h();
    }
}
class MNA{
    private void f(){
        System.out.println("MNA.f()");
    }
    class A{
        private void g(){
            System.out.println("A.g()");
        }
        class B{
            void h(){
                System.out.println("B.h()");
                // 无需任何条件就可以调用
                g();
                f();
            }
        }
    }
}