package com.kuifir.normal.innerClass;



interface Incrementable {
    void increment();
}

/**
 *只实现这个接口非常简单：
 */
class Callee1 implements Incrementable {
    private int i = 0;

    @Override
    public void increment() {
        System.out.println(++i);
    }
}

class MyIncrement {
    public void Increment() {
        System.out.println("Other operation");
    }

    static void f(MyIncrement mi) {
        mi.Increment();
    }
}

/**
 * 如果我们的类继承了MyIncrement，并且必须以其他方式实现Incrementable#increment(),则必须使用内部类
 */
class Callee2 extends MyIncrement {
    private int i = 0;

    @Override
    public void Increment() {
        super.Increment();
        System.out.println(++i);
    }

    private class Closure implements Incrementable {
        @Override
        public void increment() {
            // 需要指定调用外围类方法，否则会无限递归
            Callee2.this.Increment();
        }

    }
    Incrementable getCallbackReference() {
        return new Closure();
    }
}

class Caller{
    private Incrementable callbackReference;
    Caller(Incrementable cbh){
        this.callbackReference = cbh;
    }
    void go(){
        callbackReference.increment();
    }
}
/**
 * @author kuifir
 * @date 2023/5/7 19:45
 */
public class Callback {
    public static void main(String[] args) {
        Callee1 callee1 = new Callee1();
        Callee2 callee2 = new Callee2();
        MyIncrement.f(callee2);
        Caller caller1 = new Caller(callee1);
        Caller caller2 = new Caller(callee2.getCallbackReference());
        caller1.go();
        caller1.go();
        caller2.go();
        caller2.go();

    }
}