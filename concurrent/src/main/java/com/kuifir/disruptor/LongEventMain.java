package com.kuifir.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * 最后，我们需要这些事件的来源。为简单起见，我们假设数据来自某种 I/O 设备，例如网络或ByteBuffer.
 * 从 Disruptor 的 3.0 版开始，就可以使用 Lambda 风格的 API 来编写发布者。这是首选方法，因为它封装了备选方案的大部分复杂性。
 * <ul>
 *     <li>指定环形缓冲区的大小，必须是 2 的幂。</li>
 *     <li>构建干扰器</li>
 *     <li>连接处理程序</li>
 *     <li>启动 Disruptor，启动所有线程运行</li>
 *     <li>从 Disruptor 获取环形缓冲区以用于发布:
 *     请注意，用于的 lambda publishEvent()仅引用传入的参数。
 *     如果我们改为将该代码编写为：<pre>{@code
 *          ByteBuffer bb = ByteBuffer.allocate(8);
 *          for (long l = 0; true; l++)
 *          {
 *              bb.putLong(0, l);
 *              ringBuffer.publishEvent((event, sequence) -> event.set(bb.getLong(0)));
 *              Thread.sleep(1000);
 *          }
 *     }</pre>
 *     这将创建一个捕获 lambda，这意味着它需要实例化一个对象来保存变量，
 *     ByteBuffer bb因为它将 lambda 传递给调用publishEvent()。
 *     这将产生额外的（不必要的）垃圾，因此如果需要低 GC 压力，则应首选将参数传递给 lambda 的调用。
 *     </li>
 * </ul>
 *
 * @author kuifir
 * @date 2023/6/11 23:42
 */
public class LongEventMain {
    public static void main(String[] args) throws InterruptedException {
        // 指定环形缓冲区的大小，必须是 2 的幂。
        int bufferSize = 1024;
        // 构建干扰器
        Disruptor<LongEvent> disruptor =
                new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        // 连接处理程序
        disruptor.handleEventsWith((event, sequence, endOfBatch) ->
                System.out.println("Event" + event));
        // 启动 Disruptor，启动所有线程运行
        disruptor.start();

        // 从 Disruptor 获取环形缓冲区以用于发布。
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, 1);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
            Thread.sleep(1000);
        }
    }
}
