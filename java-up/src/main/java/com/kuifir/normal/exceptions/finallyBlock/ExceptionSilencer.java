package com.kuifir.normal.exceptions.finallyBlock;

/**
 * 还有一种更简单的会丢失异常的方式，实在finally子句中执行return;
 * @see LostMessage
 * @author kuifir
 * @date 2023/5/12 22:34
 */
public class ExceptionSilencer {
    public static void main(String[] args) {
        try {
            throw new RuntimeException();
        }finally {
            // 在finally中return，会把任何被抛出的异常被压制下来。
            return;
        }
    }
}
