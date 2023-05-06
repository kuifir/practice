package com.kuifir.normal.innerClass;

/**
 * 要生成外部类对象的引用,可以使用外部类的名字.this。
 * 这样生成的引用会自动具有正确的类型，而且是可以在编译时确定并检查的，所以没有任何运行时开销
 * @author kuifir
 */
public class DotThis {
    void f(){
        System.out.println("DotThis.f()");
    }
    public Inner inner(){
        return new Inner();
    }
    public class Inner{
        public DotThis outer(){
            return DotThis.this; // 如果直接写this，引用的会时Inner的"this"
        }
    }

    public static void main(String[] args) {
        DotThis dotThis = new DotThis();
        DotThis.Inner inner = dotThis.inner();
        inner.outer().f();
    }
}
