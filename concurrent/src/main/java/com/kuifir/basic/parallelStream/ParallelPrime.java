package com.kuifir.basic.parallelStream;

import cn.hutool.core.date.StopWatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.LongStream;

/**
 * Java8的流有个很大的好处：在某些时候，流很容易并行化。
 * 这来自库的精心设计，特别是流使用的<b>内部迭代</b>(internal iteration)的方式——也就是说，流会控制他们自身的迭代器。
 * 特别是流会使用一种称为<b>分流器</b>(spliterator)的特殊迭代器，其设计要求是要易于自动分割。
 * 这就产生了一个相当神奇的结果，即通过简单地直接使用.parallel()，流中的一切就突然都可以作为一组并行的任务来运行了。
 * 如果代码使用了Stream，那么就可以轻而易举通过并行化来提升速度。
 * 举例来说：
 * 寻找素数是个很耗时的过程，我们可以通过给程序增加计时来证明。
 *
 * @author kuifir
 * @date 2023/6/23 22:03
 */
public class ParallelPrime {
    static final int COUNT = 100_000;

    public static boolean isPrime(long n) {
        return LongStream.rangeClosed(2, (long) Math.sqrt(n))
                .noneMatch(i -> n % i == 0);
    }

    public static void main(String[] args) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> primes = LongStream.iterate(2, i -> i + 1)
//                .parallel()
                .filter(ParallelPrime::isPrime)
                .limit(COUNT)
                .mapToObj(Long::toString)
                .toList();
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());
        Files.write(Paths.get(".","primes.txt"),primes, StandardOpenOption.CREATE);
    }
}
