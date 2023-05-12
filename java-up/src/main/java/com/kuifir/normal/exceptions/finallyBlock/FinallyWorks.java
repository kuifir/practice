package com.kuifir.normal.exceptions.finallyBlock;

/**
 * 演示finally总会被执行
 *
 * Java 中的异常不允许我们回退到异常被抛出的地方。
 * @author kuifir
 * @date 2023/5/11 23:39
 */
public class FinallyWorks {
    static int count = 0;

    public static void main(String[] args) {
        while (true) {
            try {
                // 使用的是后缀自增操作符，第一次结果为0
                if (count++ == 0) {
                    throw new ThreeException();
                }
                System.out.println("No exception");
            } catch (ThreeException e) {
                System.out.println("ThreeException");
            }finally {
                System.out.println("In finally clause");
                if (count == 2){
                    break;
                }
            }
        }
    }
}

class ThreeException extends Exception {
}