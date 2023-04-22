package com.kuifir.aop;

import com.kuifir.beans.factory.FactoryBean;
import com.kuifir.util.ClassUtils;

public class ProxyFactoryBean<T> implements FactoryBean<T> {
    private AopProxyFactory<T> aopProxyFactory;
    private String[] interceptorNames;
    private String targetName;
    private T target;
    private final ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private T singletonInstance;

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory<>();
    }

    protected AopProxy<T> createAopProxy() {
        return getAopProxyFactory().createAopProxy(target);
    }

    @Override
    public T getObject() throws Exception {
        // 获取内部对象
        return getSingletonInstance();
    }

    private synchronized T getSingletonInstance() {//获取代理
        if (this.singletonInstance == null) {
            this.singletonInstance = getProxy(createAopProxy());
        }
        return this.singletonInstance;
    }

    protected T getProxy(AopProxy<T> aopProxy) {//生成代理对象
        return aopProxy.getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }

    public void setAopProxyFactory(AopProxyFactory<T> aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public AopProxyFactory<T> getAopProxyFactory() {
        return this.aopProxyFactory;
    }

    public void setInterceptorNames(String... interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }
}
