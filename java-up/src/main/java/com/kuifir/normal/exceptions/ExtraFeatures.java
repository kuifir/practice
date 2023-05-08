package com.kuifir.normal.exceptions;

/**
 * 创建自己的异常的过程可以更进一步。我们可以添加更多构造器和成员
 * 
 * @see LoggingException
 * @see LoggingExceptions
 * @see LoggingExceptions2
 */
class MyException2 extends Exception {
    private int x;

    MyException2() {
    }

    MyException2(String msg) {
        super(msg);
    }

    MyException2(String msg, int x) {
        super(msg);
        this.x = x;
    }

    public int val() {
        return x;
    }

    @Override
    public String getMessage() {
        return "Detail Message: " + x + "" + super.getMessage();
    }
}

/**
 * @author kuifir
 * @date 2023/5/8 23:30
 */
public class ExtraFeatures {
    public static void f() throws MyException2 {
        System.out.println("Throwing MyException2 from f()");
        throw new MyException2();
    }

    public static void g() throws MyException2 {
        System.out.println("Throwing myException2 from g()");
        throw new MyException2("Originated in g()");
    }

    public static void h() throws MyException2 {
        System.out.println("Throwing Exception2 from h()");
        throw new MyException2("Originated in h()", 47);
    }

    public static void main(String[] args) {
        try {
            f();
        }catch (MyException2 e){
            e.printStackTrace(System.out);
        }
        try {
            g();
        }catch (MyException2 e){
            e.printStackTrace(System.out);
        }
        try {
            h();
        }catch (MyException2 e){
            e.printStackTrace(System.out);
            System.out.println("e.val() = " + e.val());
        }
    }
}
