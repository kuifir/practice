package com.kuifir.basic.atomics;

/**
 * 此处getAsInt()看似是安全的原子操作，但是Atomicity.test()自语道非偶数数值时会失败。
 * 虽然 return i确实是原子操作，但这里未加上同步，这会导致该值能在对象处于不稳定的中间状态时被读到。
 * 此外，i也不是volatile的，因此会存在可见性问题。
 * getValue()和evenIncrement()都必须是synchronized的（这样可以在i未被设为volatile的情况下，依然保证其线程安全性）
 *{@link SafeReturn}
 * @author kuifir
 * @date 2023/6/30 23:32
 */
public class UnsafeReturn extends IntTestable {
    private int i = 0;

    @Override
    void evenIncrement() {
        i++;
        i++;
    }

    @Override
    public int getAsInt() {
        return i;
    }

    public static void main(String[] args) {
        Atomicity.test(new UnsafeReturn());
    }
}
