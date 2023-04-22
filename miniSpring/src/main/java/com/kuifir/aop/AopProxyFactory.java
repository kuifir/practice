package com.kuifir.aop;

public interface AopProxyFactory<T> {
    AopProxy<T> createAopProxy(T target);
}
