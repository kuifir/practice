package com.kuifir.workerThread;

import lombok.Data;

/**
 * Worker 是工人的意思，代表在 Worker Thread 设计模式中，
 * 会有一些工人（线程）不断轮流处理过来的工作，当没有工作时，
 * 工人则会处于等待状态，直到有新的工作进来。
 * 除了工人角色，Worker Thread 设计模式中还包括了流水线和产品。
 * <p>
 * 通过 Worker Thread 设计模式来完成一个物流分拣的作业。
 * 假设一个物流仓库的物流分拣流水线上有 8 个机器人，它们不断从流水线上获取包裹并对其进行包装，送其上车。
 * 当仓库中的商品被打包好后，会投放到物流分拣流水线上，而不是直接交给机器人，机器人会再从流水线中随机分拣包裹。
 * <p>
 * 包裹类
 */
@Data
public class Package {
    private String name;
    private String address;
    public void execute(){
        System.out.println(Thread.currentThread().getName()+ "execute" + this);
    }
}
