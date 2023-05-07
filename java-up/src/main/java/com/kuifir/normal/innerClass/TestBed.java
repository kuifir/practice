package com.kuifir.normal.innerClass;

/**
 * 可以使用嵌套类来存放测试代码
 * @author kuifir
 * @date 2023/5/7 16:28
 */
public class TestBed {
    public void f(){
        System.out.println("f()");
    }
    public static class Tester{
        public static void main(String[] args) {
            TestBed testBed = new TestBed();
            testBed.f();
        }
    }
}
