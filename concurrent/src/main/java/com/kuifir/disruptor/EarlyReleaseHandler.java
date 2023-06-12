package com.kuifir.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.Sequence;

/**
 * “提前发布”示例
 *
 * @author kuifir
 * @date 2023/6/12 20:56
 */
public class EarlyReleaseHandler implements EventHandler<LongEvent> {
    private Sequence sequenceCallback;
    private int batchRemaining = 20;

    @Override
    public void setSequenceCallback(final Sequence sequenceCallback) {
        this.sequenceCallback = sequenceCallback;
    }

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        processEvent(event);
        boolean logicalChunkOfWorkComplete = isLogicalChunkOfWorkComplete();
        if (logicalChunkOfWorkComplete) {
            sequenceCallback.set(sequence);
        }

        batchRemaining = logicalChunkOfWorkComplete || endOfBatch ? 20 : batchRemaining;
    }

    private boolean isLogicalChunkOfWorkComplete() {
        // Ret true or false based on whatever criteria is required for the smaller
        // chunk.  If this is doing I/O, it may be after flushing/syncing to disk
        // or at the end of DB batch+commit.
        // Or it could simply be working off a smaller batch size.

        return --batchRemaining == -1;
    }

    private void processEvent(final LongEvent event) {
        // Do processing
    }
}
