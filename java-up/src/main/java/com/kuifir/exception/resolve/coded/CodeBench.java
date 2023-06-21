package com.kuifir.exception.resolve.coded;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * 使用了错误码之后，抛出异常与正常运行性能相近
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Fork(3)
public class CodeBench {
    @Param({"SHA-128", "SHA-256"})
    private static String s;


    @Benchmark
    public void TestWithoutCoded() {
        try {
            Digest.of(s);
        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public void TestWithCoded() {
        Digest.ofWithCoded(s);

    }


    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(CodeBench.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
