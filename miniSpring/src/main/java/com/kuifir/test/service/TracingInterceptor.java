package com.kuifir.test.service;

import com.kuifir.aop.MethodInterceptor;
import com.kuifir.aop.MethodInvocation;

import java.util.Arrays;

public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation i) throws Throwable {
        System.out.println("method " + i.getMethod() + " is called on " + i.getThis() + " with args " + Arrays.toString(i.getArguments()));
        Object ret = i.proceed();
        System.out.println("method " + i.getMethod() + " returns " + ret);
        return ret;
    }
}
