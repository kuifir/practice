package com.kuifir.basic.completableFuture.exceptions;

/**
 * 通过正整型的failcount(失败数)，每次向work()方法传递对象，failcount都会递减。
 * 当它等于0的时候，work()会抛出异常。如果直接传入值为0的failcount,则永远不会抛出异常。
 * 注意，该程序在异常抛出时会发出异常报告。
 *
 * @author kuifir
 * @date 2023/6/27 21:52
 */
public class Breakable {
    String id;
    private int failcount;

    public Breakable(String id, int failcount) {
        this.id = id;
        this.failcount = failcount;
    }

    @Override
    public String toString() {
        return "Breakable_" + id + "[" + failcount + "]";
    }

    public static Breakable work(Breakable b) {
        if (--b.failcount == 0) {
            System.out.println("Throwing Exception for " + b.id + "");
            throw new RuntimeException("Breakable_" + b.id + " failed");
        }
        System.out.println(b);
        return b;
    }
}
