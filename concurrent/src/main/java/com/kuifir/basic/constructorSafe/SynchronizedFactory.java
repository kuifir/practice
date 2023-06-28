package com.kuifir.basic.constructorSafe;

/**
 * @author kuifir
 * @date 2023/6/28 23:47
 */
public class SynchronizedFactory {
    public static void main(String[] args) {
        Unsafe unsafe = new Unsafe();
        IDChecker.test(() -> SyncFactory.factory(unsafe));
    }
}

final class SyncFactory implements HasID {
    private final int id;

    private SyncFactory(SharedArg sa) {
        id = sa.get();
    }

    @Override
    public int getID() {
        return id;
    }

    public static synchronized SyncFactory factory(SharedArg sa) {
        return new SyncFactory(sa);
    }
}
