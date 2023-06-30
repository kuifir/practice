package com.kuifir.basic.lowlevel;

import com.kuifir.basic.task.Nap;

/**
 * @author kuifir
 * @date 2023/6/30 22:42
 */
public class EvenProducer extends IntGenerator {
    private int currentEvenValue = 0;

    @Override
    public int next() {
        ++currentEvenValue;
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenProducer());
    }
}
