package com.kuifir.aop;

public class DefaultAopProxyFactory<T> implements AopProxyFactory<T> {
    @Override
    public AopProxy<T> createAopProxy(T target, PointcutAdvisor advisor) {
        return new JdkDynamicAopProxy<>(target, advisor);
    }
}
