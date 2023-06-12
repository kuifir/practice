package com.kuifir.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * 最后一步是将整个东西连接在一起。虽然可以手动连接每个组件，但这可能很复杂，因此提供了 DSL 来简化构造。
 *
 * @author kuifir
 * @date 2023/6/12 20:37
 * @see LongEventProducer
 */
public class LongEventMain3 {
    public static void main(String[] args) throws InterruptedException {
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor =
                new Disruptor<>(
                        factory,
                        bufferSize,
                        DaemonThreadFactory.INSTANCE,
                        // ProducerType#SINGLE用于创建一个SingleProducerSequencer
                        // ProducerType#MULTI用来创建一个MultiProducerSequence
                        ProducerType.SINGLE,
                        // 设置所需的WaitStrategy
                        // WaitStrategyDisruptor默认使用的是BlockingWaitStrategy.
                        // 在内部BlockingWaitStrategy使用典型的锁和条件变量来处理线程唤醒。
                        // 这BlockingWaitStrategy是可用等待策略中最慢的，但在 CPU 使用率方面是最保守的，并且将在最广泛的部署选项中提供最一致的行为。
                        new BlockingWaitStrategy());
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            producer.onData(bb);
            Thread.sleep(1000);
        }
    }
}
