package com.kuifir.normal.exceptions;

/**
 * @author kuifir
 * @date 2023/5/8 22:46
 */
class MyException extends Exception {
    MyException() {
    }

    MyException(String msg) {
        super(msg);
    }
}
/**
 * @author kuifir
 */
public class FullConstructors{
    public static void f() throws MyException{
        System.out.println("throwing MyException from f()");
        throw new MyException();
    }
    public static void g() throws MyException{
        System.out.println("throwing MyException from g()");
        throw new MyException("originated in g()");
    }

    public static void main(String[] args) {
        try {
            f();
        }catch (MyException e){
            e.printStackTrace(System.out);
        }
        try {
            g();
        }catch (MyException e){
            e.printStackTrace(System.out);
        }
    }
}
