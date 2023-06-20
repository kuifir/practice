package com.kuifir.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 一旦我们定义了事件，我们就需要创建一个消费者来处理这些事件。例如，我们将创建一个EventHandler将值打印到控制台的。
 * 最后，我们需要这些事件的来源。为简单起见，我们假设数据来自某种 I/O 设备，例如网络或ByteBuffer.{@link LongEventMain}
 *
 * @author kuifir
 * @date 2023/6/11 23:38
 * @see LongEvent
 * @see LongEventFactory
 * @see LongEventMain
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Event: " + longEvent);
    }
}
