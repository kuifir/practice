package com.kuifir.disruptor;

/**
 * 首先，我们将定义Event:携带数据
 *
 * @author kuifir
 * @date 2023/6/11 23:34
 */
public class LongEvent {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent{" +
                "value=" + value +
                '}';
    }
}
