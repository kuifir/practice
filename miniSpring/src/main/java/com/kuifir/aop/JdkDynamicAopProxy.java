package com.kuifir.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy<T> implements AopProxy<T>, InvocationHandler {
    private final T target;
    private final Advisor advisor;

    public JdkDynamicAopProxy(T target, Advisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public T getProxy() {
        return (T) Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("doAction")) {
//            System.out.println("-----before call real object, dynamic proxy........");
            Class<?> targetClass = (target != null ? target.getClass() : null);
            MethodInterceptor interceptor = this.advisor.getMethodInterceptor();
            MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass);
            return interceptor.invoke(invocation);
        }
        return method.invoke(target, args);
    }
}
