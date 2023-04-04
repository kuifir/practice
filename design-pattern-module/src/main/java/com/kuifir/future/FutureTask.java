package com.kuifir.future;

public class FutureTask<T> implements Future<T> {
    private T result;
    private boolean isDone = false;
    private final Object LOCK = new Object();

    @Override
    public T get() {
        synchronized (LOCK) {
            while (!done()) {
                try {
                    LOCK.wait(); //阻塞等待
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    public void finish(T result) {
        synchronized (LOCK) {
            if (isDone) {
                return;
            }
            this.result = result;
            this.isDone = true;
            LOCK.notifyAll();
        }
    }

    @Override
    public boolean done() {
        return isDone;
    }
}
