package com.kuifir.basic.atomics;

import java.util.function.IntSupplier;

/**
 * 用下面这段代码来测试原子性这个概念：定义一个抽象类，在其中写一个方法，将一个整型变量按偶数自增，然后由run()方法持续调用该方法。
 * {@link IntSupplier}是一个带有getAsInt()方法的函数式接口。
 * @author kuifir
 * @date 2023/6/30 23:25
 */
public abstract class IntTestable implements Runnable, IntSupplier {
    /**
     * 将一个整型变量按偶数自增
     */
    abstract void evenIncrement();
    @Override
    public void run(){
        while (true){
            evenIncrement();
        }
    }
}
