package com.kuifir.test.service;

import java.lang.reflect.Proxy;

public class DynamicProxy {
    private final Object subject;

    public DynamicProxy(Object subject) {
        this.subject = subject;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(DynamicProxy.class.getClassLoader(),
                subject.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    if (method.getName().equals("doAction")) {
                        System.out.println("before call real object........");
                    }
                    return method.invoke(subject, args);
                });
    }
}
