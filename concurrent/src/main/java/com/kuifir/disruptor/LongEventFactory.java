package com.kuifir.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 为了让 Disruptor 为我们预先分配这些事件，我们需要一个EventFactory将执行构造的。
 * 这可以是方法引用，例如接口LongEvent::new的显式实现EventFactory
 *
 * @see LongEvent
 * @author kuifir
 * @date 2023/6/11 23:36
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
