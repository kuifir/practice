package com.kuifir.basic.completableFuture.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * 检查型异常
 * CompletableFuture和并行Stream都不支持包含检查型异常的操作。
 * 因此，你必须在调用操作的时候处理检查型异常，而这明显会降低代码的优雅性：
 * 如果想像使用nochecked()的引用一样，使用withchecked()的方法引用，
 * 编译器会报错，所以你必须写出lambda表达式处理异常（或写一个不会抛出异常的包装方法）
 * @author kuifir
 * @date 2023/6/27 23:34
 */
public class ThrowsChecked {
    class Checked extends Exception {
    }

    static ThrowsChecked nochecked(ThrowsChecked tc) {
        return tc;
    }

    static ThrowsChecked withchecked(ThrowsChecked tc) throws Checked {
        return tc;
    }

    static void testStream() {
        Stream.of(new ThrowsChecked())
                .map(ThrowsChecked::nochecked)
//                .map(ThrowsChecked::withchecked)
                .map(throwsChecked -> {
                    try {
                        return withchecked(throwsChecked);
                    } catch (Checked e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    static void testCompletableFuture() {
        CompletableFuture
                .completedFuture(new ThrowsChecked())
                .thenApply(ThrowsChecked::nochecked)
//                .thenAccept(ThrowsChecked::withchecked)
                .thenApply(throwsChecked -> {
                    try {
                        return withchecked(throwsChecked);
                    } catch (Checked e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
