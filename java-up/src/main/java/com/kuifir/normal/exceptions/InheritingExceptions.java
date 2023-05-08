package com.kuifir.normal.exceptions;

/**
 * 创建一个自己的异常类，可以继承现有的异常类。
 * 最简单的方法就是，让编译器为我们创建无参构造器，几乎不需要任何代码。
 * @author kuifir
 * @date 2023/5/8 22:31
 */
class SimpleException extends Exception {
    /**
     *编译器创建了一个无参构造器，它会自动（而且是隐式地)调用基类的无参构造器。
     */
}

public class InheritingExceptions {
    public void f() throws SimpleException{
        System.out.println("throw SimpleException from f()");
        throw  new SimpleException();
    }

    public static void main(String[] args) {
        InheritingExceptions sed = new InheritingExceptions();
        try {
            sed.f();
        }catch (SimpleException e){
            System.out.println("caught it");
        }
    }
}
