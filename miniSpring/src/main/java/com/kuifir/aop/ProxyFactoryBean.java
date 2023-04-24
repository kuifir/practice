package com.kuifir.aop;

import com.kuifir.aop.advice.*;
import com.kuifir.beans.BeansException;
import com.kuifir.beans.factory.BeanFactory;
import com.kuifir.beans.factory.BeanFactoryAware;
import com.kuifir.beans.factory.FactoryBean;
import com.kuifir.util.ClassUtils;

public class ProxyFactoryBean<T> implements FactoryBean<T>, BeanFactoryAware {
    private AopProxyFactory<T> aopProxyFactory;
    private String[] interceptorNames;
    private String targetName;
    private T target;
    private final ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private T singletonInstance;

    private BeanFactory beanFactory;
    private String interceptorName;
    private PointcutAdvisor advisor;

    private synchronized void initializeAdvisor() {
        Object advice = null;
        MethodInterceptor mi = null;
        try {
            advice = this.beanFactory.getBean(this.interceptorName);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        this.advisor = (PointcutAdvisor) advice;
    }

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory<>();
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected AopProxy<T> createAopProxy() {
        return getAopProxyFactory().createAopProxy(target, this.advisor);
    }

    @Override
    public T getObject() throws Exception {
        initializeAdvisor();
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

    public String getTargetName() {
        return targetName;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }
}
