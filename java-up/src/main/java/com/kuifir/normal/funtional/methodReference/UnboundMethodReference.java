package com.kuifir.normal.funtional.methodReference;

/**
 * 未绑定对象的方法引用
 * 未绑定方法引用指的是尚未关联到某个对象的普通（非静态）方法。
 * 对于未绑定引用，必须先提供对象，然后才能使用
 *<p/>
 * 即使{@link MakeString#make()}的签名与{@link X#f()}方法签名一致。
 *  MakeString ms = X::f;
 *  会编译报错:java: 不兼容的类型: 方法引用无效(invalid method reference )
 *       在未绑定查找中找到意外的实例 方法 f()
 * 问题在于，这里事实上还涉及另一个（隐藏的）参数：this。
 * 如果没有一个可供附着的X对象，就无法调用f();
 * <p/>
 * 因此X::f 代表的是一个未绑定方法的引用，因为它没有"绑定到"某个对象
 * 为解决这个问题，我们需要一个X对象，所以我们的接口事实上还需要一个额外的参数，
 * 如{@link TransformX}所示。如果将X::f 赋值给一个TransformX,Java会开心接受。
 * <p/>
 * 另外，在未绑定引用的情况下，函数式方法（接口中的单一方法）的签名与方法引用的签名不在完全匹配。
 * 这样做有一个很好的理由，那就是我们需要一个对象，让其在方法上调用。
 * <p>
 * 如果有方法有更多参数，只要遵循第一个参数取得是this这种模式{@link MultiUnbound}
 * @see X
 * @see MakeString
 * @see TransformX
 * @author kuifir
 * @date 2023/5/13 15:08
 */
public class UnboundMethodReference {
    public static void main(String[] args) {
        // MakeString ms = X::f;
        // 会编译报错:java: 不兼容的类型: 方法引用无效
        //    在未绑定查找中找到意外的实例 方法 f()
        TransformX sp = X::f;
        X x = new X();
        // 我们接受了未绑定引用，然后以X为参数在其上调用了transform(),最终以某种方式调用了x.f()。
        // java知道它必须接受第一个参数，事实上就是this,并在它的上面调用该方法。
        System.out.println(sp.transform(x));
        System.out.println(x.f());
    }
}

class X {
    String f() {
        return "X::f()";
    }
}
interface MakeString{
    String make();
}
interface TransformX{
    String transform(X x);
}