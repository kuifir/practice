package com.kuifir.normal.exceptions;

/**
 * 重新抛出一个与所捕获的异常不同的异常也可以返回新的栈轨迹信息。
 * 这样会得到与使用fillInStackTrace()类似的效果，关于这个异常的原始调用点的信息会丢失，剩下的是与新的throw有关的信息
 * @see Rethrowing
 * @see Throwable#fillInStackTrace()
 * @author kuifir
 * @date 2023/5/11 22:13
 */
public class RethrowNew {
    public static void f() throws OneException {
        System.out.println("originating the exception in f()");
        throw new OneException("throw from f()");
    }

    public static void main(String[] args) {
        try {
            f();
        } catch (OneException e) {
            System.out.println("Caught in inner try , e.printStackTrace");
            e.printStackTrace(System.out);
            try {
                throw new TwoException("from inner try");
            } catch (TwoException ex) {
                System.out.println("Caught in outer try, e.printStackTrace");
                // 最后的异常只知道来自内部的try块，而不知道它来自f()
                ex.printStackTrace(System.out);
            }
        }
    }
}

class OneException extends Exception {
    OneException(String s) {
        super(s);
    }
}
class TwoException extends Exception {
    TwoException(String s) {
        super(s);
    }
}