package com.kuifir.normal.innerClass;

/**
 * 继承一个内部类:
 * 因为内部类的构造器必须附着到一个指向其包围类的对象的引用上，所以当你要继承内部类时，事情就稍微有点复杂了。
 * 问题在于，这个"秘密"的引用必须初始化，然而在子类中并没有默认的对象供其附着。
 * 必须使用一种特殊的语法来明确地指出这种关联
 * @author kuifir
 * @date 2023/5/7 23:21
 */
public class InheritInner extends WithInner.Inner{
    /**
     * InheritInner(){} // 不能编译
     * InheritInner只继承了内部类，而不是外围类。但是当需要创建构造器时，默认构造器是行不通的。
     * 只传递一个指向其他包围类对象的引用是不够的，除此之外，还必须在构造器内使用如下语法：
     * enclosingClassReference.super();
     * 这样就提供了必须的引用，然后程序才能编译通过。
     */
    InheritInner(WithInner wi){
        wi.super();
    }

    public static void main(String[] args) {
        WithInner wi = new WithInner();
        InheritInner ii = new InheritInner(wi);
    }

}
class WithInner{
    class Inner{}
}

