package com.kuifir.normal.stream.optional;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

/**
 * 假设有一个可能会生成null值的生成器，如果使用这个生成器创建了一个流，我们像将这些元素包在Optional中。它看上去应该是这样的
 */
public class Signal {
    private final String msg;
    static Random rand = new Random(47);

    public static Signal morse() {
        switch (rand.nextInt(4)) {
            case 1 -> {
                return new Signal("dot");
            }
            case 2 -> {
                return new Signal("dash");
            }
            default -> {
                return null;
            }
        }
    }

    public static Stream<Optional<Signal>> stream() {
        return Stream.generate(Signal::morse)
                .map(Optional::ofNullable);
    }

    public Signal(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
