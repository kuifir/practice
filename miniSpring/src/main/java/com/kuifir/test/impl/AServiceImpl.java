package com.kuifir.test.impl;

import com.kuifir.test.AService;

public class AServiceImpl implements AService {
    @Override
    public void sayHello() {
        System.out.println("a service say hello");
    }
}
