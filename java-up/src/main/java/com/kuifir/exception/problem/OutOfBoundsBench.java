package com.kuifir.exception.problem;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Fork(1)
public class OutOfBoundsBench {
    private static String s = "Hello, world!";  // s.length() == 13.

    @Benchmark
    public void withException() {
        try {
            s.substring(14);
        } catch (RuntimeException re) {
            // blank line, ignore the exception.
        }
    }

    @Benchmark
    public void noException() {
        try {
            s.substring(13);
        } catch (RuntimeException re) {
            // blank line, ignore the exception.
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(OutOfBoundsBench.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
