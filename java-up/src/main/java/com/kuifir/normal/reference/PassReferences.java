package com.kuifir.normal.reference;

/**
 * 当你将一个引用传给方法后，该引用指向的仍然是原来的对象。
 * @author kuifir
 * @date 2023/6/3 22:37
 */
public class PassReferences {
    public static void f(PassReferences h){
        System.out.println("h inside f(): " + h);
    }

    public static void main(String[] args) {
        PassReferences p = new PassReferences();
        System.out.println("p inside main(): "+p);
        f(p);
    }
}
