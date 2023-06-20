package com.kuifir.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 我们可以使用一种更“原始”的方法：
 * 　显而易见的是，事件发布变得比使用简单队列更加复杂。
 * 这是由于需要事件预分配。
 * 它需要（在最低级别）消息发布的两阶段方法，即在环形缓冲区中声明插槽，然后发布可用数据。
 * - 还需要将发布包装在try/finally块中。
 * 如果我们在 Ring Buffer 中声明一个插槽（调用RingBuffer#next()），那么我们必须发布这个序列。
 * 如果不这样做，可能会导致 Disruptor 状态的损坏。
 * 具体来说，在多生产者的情况下，这将导致消费者停滞不前并且无法在不重启的情况下恢复。
 * 因此，建议EventTranslator使用 lambda 或 API。
 * 最后一步是将整个东西连接在一起。虽然可以手动连接每个组件，但这可能很复杂，因此提供了 DSL 来简化构造。
 *
 * @author kuifir
 * @date 2023/6/12 20:33
 * @see LongEventProducer
 */
public class LongEventProducer2 {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer2(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        // 抓住下一个序列
        long sequence = ringBuffer.next();
        try {
            // 	获取该序列在 Disruptor 中的条目
            LongEvent event = ringBuffer.get(sequence);
            //　用数据填充条目
            event.set(bb.getLong(0));
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
