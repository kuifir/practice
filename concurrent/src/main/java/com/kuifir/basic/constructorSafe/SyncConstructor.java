package com.kuifir.basic.constructorSafe;

/**
 * 对Unsafe类的共享现在是安全的了。
 * @author kuifir
 * @date 2023/6/28 23:39
 */
public class SyncConstructor implements HasID {
    private final int id;
    private static Object constructorLock = new Object();

    public SyncConstructor(SharedArg sa) {
        synchronized (constructorLock) {
            id = sa.get();
        }
    }

    @Override
    public int getID() {
        return id;
    }

    public static void main(String[] args) {
        Unsafe unsafe = new Unsafe();
        IDChecker.test(() -> new SyncConstructor(unsafe));
    }
}
