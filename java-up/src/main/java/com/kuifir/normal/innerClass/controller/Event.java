package com.kuifir.normal.innerClass.controller;

import java.time.Duration;
import java.time.Instant;

/**
 * 用于任何控制事件的公共方法
 * 用于描述任何控制时间的接口，是一个abstract类，而不是实际的接口。其中包含了部分实现、
 * @author kuifir
 * @date 2023/5/7 20:27
 */
public abstract class Event {
    private Instant eventTime;
    protected final Duration delayTime;

    /**
     * 构造器会获取你希望Event运行的时间（从对象创建时开始计时，以毫秒计算），
     * 然后调用start(),该方法会取得当前时间，再加上延迟事件，从而生成时间将要发生的时间。
     *
     * @param millisecondDelay
     */
    public Event(long millisecondDelay){
        delayTime = Duration.ofMillis(millisecondDelay);
        start();
    }

    /**
     * start()被实现为一个单独的方法，而不是直接是现在构造器中。
     * 通过这种方式，就可以在事件执行完毕之后重启定时器，从而可以复用Event对象。
     */
    public void start(){ // 可以重启
        eventTime = Instant.now().plus(delayTime);
    }

    /**
     * ready()告诉何时可以运行action()方法。不过可以在子类中重写ready()，让Event()基于时间以外的因素触发。
     * @return
     */
    public boolean ready(){
        return Instant.now().isAfter(eventTime);
    }
    public abstract void action();
}
