package com.kuifir.test.service.impl;

import com.kuifir.test.service.IAction;

public class Action2 implements IAction {
    @Override
    public void doAction() {
        System.out.println("really do action");
    }

    @Override
    public void doSomething() {
        System.out.println("really do something");
    }
}
