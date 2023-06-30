package com.kuifir.basic.lowlevel;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 创建一个抽象类，它包含了{@link EvenChecker}需要用到的最小必要方法集——next()方法，而且它可以被取消。
 * cancel()会改变canceled标志的状态，而isCancel()则会告诉你该标志是否被设置了。
 * 由于canceled是个AtomicBoolean,所以它是原子的，这意味着诸如赋值和返回值这样的简单操作不会相互冲突，
 * 因此你不会看到该字段在进行这些简单操作时的中间状态。
 * @author kuifir
 * @date 2023/6/30 21:47
 */
public abstract class IntGenerator {
    private AtomicBoolean canceled = new AtomicBoolean();
    public abstract int next();
    public void cancel(){
        canceled.set(true);
    }
    public boolean isCanceled(){
        return canceled.get();
    }
}
