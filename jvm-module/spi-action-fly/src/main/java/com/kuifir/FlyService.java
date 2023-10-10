package com.kuifir;

/**
 * @author kuifir
 * @date 2023/10/10 23:04
 */
public class FlyService implements ActionInterface,Runnable{
    @Override
    public void action() {
        System.out.println("I'm flying");
    }

    @Override
    public void run() {
        System.out.println("I'm flying");
    }
}
