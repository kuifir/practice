package com.kuifir.automaticMemoryManagement.memoryAllocation;

/**
 *
 *  Minor GC
 *  收集器: Serial+ Serial Old
 * 对象优先在Eden分配，大多数情况下，对象在新生代Eden区中分配。
 * 当Eden区没有足够空间进行分配时，虚拟机将发起一次Minor GC
 *
 *  VM args:
 *  限制堆大小为20M，不可拓展，其中10MB分配给新生代，剩下的10MB分配给老年代
 *  -verbose:gc -Xms20M -Xmx20M -Xmn10M
 *  新生代中Eden区和Survivor区的空间比例时8：1，
 *  -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 */
public class TestMinorGCAllocation {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];

    }
}
