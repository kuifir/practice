package com.kuifir.test.service.impl;

import com.kuifir.test.service.IAction;

public class Action1 implements IAction {
    @Override
    public void doAction() {
        System.out.println("really do action");
    }
}
