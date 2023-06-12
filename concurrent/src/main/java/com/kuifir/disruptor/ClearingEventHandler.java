package com.kuifir.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * 当通过 Disruptor 传递数据时，对象的寿命可能比预期的要长。为避免发生这种情况，可能需要在处理事件后将其清除。
 * 如果您只有一个事件处理程序，则清除同一处理程序中的值就足够了。
 * 如果你有一个事件处理程序链，那么你可能需要一个特定的处理程序放在链的末尾来处理清除对象。
 *
 * @author kuifir
 * @date 2023/6/12 20:49
 */
public class ClearingEventHandler<T> implements EventHandler<ObjectEvent<T>> {

    @Override
    public void onEvent(ObjectEvent<T> event, long sequence, boolean endOfBatch) {
        // 未能在此处调用clear()将导致与事件关联的对象在被覆盖之前一直存在。这只会在环形缓冲区环绕到开头时才会发生。
        event.clear();
    }

    public static void main(String[] args) {
        Disruptor<ObjectEvent<String>> disruptor = new Disruptor<>(
                () -> new ObjectEvent<>(), 1024, DaemonThreadFactory.INSTANCE);

        disruptor
                .handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event" + event))
                .then(new ClearingEventHandler());
    }
}

class ObjectEvent<T> {
    T val;

    void clear() {
        val = null;
    }
}