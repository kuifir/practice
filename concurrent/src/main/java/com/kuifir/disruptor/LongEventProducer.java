package com.kuifir.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 在 3.0 版之前，发布消息的首选方式是通过事件发布者/事件翻译器接口：
 *这种方法使用了一些在使用 lambda 时没有明确要求的额外类（例如处理程序、翻译器）。这里的优点是可以将翻译器代码拉到一个单独的类中并轻松进行单元测试。
 *
 * Disruptor 提供了许多不同的接口（EventTranslator、EventTranslatorOneArg、EventTranslatorTwoArg等），
 * 可以实现这些接口来提供翻译器。这是为了允许将转换器表示为静态类和 lambda{@link LongEventMain}。
 * 翻译方法的参数通过对环形缓冲区的调用传递给翻译器。
 * @author kuifir
 * @date 2023/6/12 20:26
 */
public class LongEventProducer {
    private RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
        @Override
        public void translateTo(LongEvent event, long sequence, ByteBuffer bb) {
            event.set(bb.getLong(0));
        }
    };

    public void onData(ByteBuffer bb) {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}
