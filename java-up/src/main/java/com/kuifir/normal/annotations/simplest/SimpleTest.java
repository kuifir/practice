package com.kuifir.normal.annotations.simplest;

/**
 * {@link Simple}测试
 * 此类用{@link Simple}注解了所有{@link java.lang.annotation.Target}声明所允许的内容
 * SimpleTest.java只要求Simple.java能成功编译，虽然编译的过程中什么都没有发生。
 * javac 允许使用@Simple注解(只要它还存在)，但是并不会对它做任何事，
 * 直到我们创建里一个注解处理器，并将其绑定到编译器中。
 * {@link SimpleProcessor}是个非常简单的处理器，它所做的只是打印注解的信息：
 *
 * @author kuifir
 * @date 2023/6/13 23:38
 */
@Simple
public class SimpleTest {
    @Simple
    int i;

    @Simple
    public SimpleTest() {

    }

    @Simple
    public void foo() {
        System.out.println("SimpleTest.foo()");
    }

    @Simple
    public void bar(String s, int i, float f) {
        System.out.println("SimpleTest.bar()");
    }

    @Simple
    public static void main(String[] args) {
        @Simple
        SimpleTest st = new SimpleTest();
        st.foo();
    }
}
