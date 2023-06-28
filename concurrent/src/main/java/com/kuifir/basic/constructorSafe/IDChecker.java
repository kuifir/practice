package com.kuifir.basic.constructorSafe;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link MakeObjects}类是个Supplier,通过get()方法来生成List<Integer>。
 * 该List是通过从每个{@link HasID}对象中提取id而生成的。
 * test()方法创建了两个并行的CompletableFuture来运行MakeObjects的supplier，
 * 然后接受两者的结果，并通过Guava库Sets.intersection()来找出这两个List<Integer>中有多少个id是相同的（Guava比RetainAll()快很多）。
 * @author kuifir
 * @date 2023/6/28 23:05
 */
public class IDChecker {
    public static final int SIZE = 100_000;

    static class MakeObjects implements Supplier<List<Integer>> {
        private Supplier<HasID> gen;

        MakeObjects(Supplier<HasID> gen) {
            this.gen = gen;
        }

        @Override
        public List<Integer> get() {
            return Stream.generate(gen)
                    .limit(SIZE)
                    .map(HasID::getID)
                    .collect(Collectors.toList());
        }
    }

    public static void test(Supplier<HasID> gen) {
        CompletableFuture<List<Integer>> groupA = CompletableFuture.supplyAsync(new MakeObjects(gen));
        CompletableFuture<List<Integer>> groupB = CompletableFuture.supplyAsync(new MakeObjects(gen));
        groupA.thenAcceptBoth(groupB, (a, b) -> {
            System.out.println(Sets.intersection(Sets.newHashSet(a), Sets.newHashSet(b)).size());
        }).join();

    }
}
