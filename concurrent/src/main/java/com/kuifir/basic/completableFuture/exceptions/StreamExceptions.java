package com.kuifir.basic.completableFuture.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * 我们对{@link CompletableExceptions}做些改动，
 * 来看看{@link CompletableFuture}的异常和{@link Stream}的异常有何区别:
 * 使用CompletableExceptions时，我们看到了测试A到测试E的执行过程。
 * 但是使用Stream时，甚至直到你用到终结操作（如foreach()）之前，什么都不会发生。
 * CompletableFuture会执行任务，并捕捉任何异常，以备后续的的结果返回。
 * 因为Stream的机制实在终结操作前不会做任何事，所以这两者并不好直接比较。不过Stream肯定不会保存异常
 *
 * @author kuifir
 * @date 2023/6/27 23:19
 */
public class StreamExceptions {
    static Stream<Breakable> test(String id, int failcount) {
        return Stream.of(new Breakable(id, failcount))
                .map(Breakable::work)
                .map(Breakable::work)
                .map(Breakable::work)
                .map(Breakable::work);
    }

    public static void main(String[] args) {
        // 甚至没有进行任何操作
        test("A", 1);
        test("B", 2);
        Stream<Breakable> c = test("C", 3);
        test("D", 4);
        test("E", 5);
        // 。。。直到出现终结操作
        System.out.println("Entering try");
        try {
            c.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
