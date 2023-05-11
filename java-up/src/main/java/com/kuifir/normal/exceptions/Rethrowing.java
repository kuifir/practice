package com.kuifir.normal.exceptions;

/**
 * 重新抛出异常，并要加入新的栈轨迹信息：
 * <p/>
 * 如果重新抛出当前的异常，在printStackTrace()中打印的关于异常的信息，
 * 仍将是原来的异常抛出点的信息，而不是重新抛出异常的地方的信息。
 * 要加入新的栈轨迹信息，可以调用fillInStackTrace(),它会返回一个Throwable对象，
 * 这个对象是它通过 当前栈的信息塞到原来的异常对象中 而创建的
 * 因为catch捕获了一个BaseException,所以编译器会强制我们声明catcher() throws BaseException,
 * 尽管它实际上抛出的是更具体的DerivedException.
 * 从Java 7 开始，这段代码可以编译了，这个修复虽然很小，但很有用。
 *
 * @see Throwable#fillInStackTrace()
 * @see BaseException
 * @see DerivedException
 * @author kuifir
 * @date 2023/5/11 21:53
 */
public class Rethrowing {
    public static void f() throws Exception {
        System.out.println("origination the exception in f()");
        throw new Exception("thrown from f()");
    }

    public static void g() throws Exception {
        try {
            f();
        } catch (Exception e) {
            System.out.println("Inside g(), e.printStackTrace()");
            e.printStackTrace(System.out);
            throw e;
        }
    }

    public static void h() throws Exception {
        try {
            f();
        } catch (Exception e) {
            System.out.println("Inside h(), e.printStackTrace()");
            // 这一行成为异常的新起点
            e.printStackTrace(System.out);
            throw (Exception) e.fillInStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            g();
        } catch (Exception e) {
            System.out.println("main: printStackTrace()");
            e.printStackTrace(System.out);
        }
        try {
            h();
        } catch (Exception e) {
            System.out.println("main: printStackTrace()");
            e.printStackTrace(System.out);
        }

    }
}
