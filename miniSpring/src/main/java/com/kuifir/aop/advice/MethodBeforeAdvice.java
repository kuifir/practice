package com.kuifir.aop.advice;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends BeforeAdvice{
    void before(Method method, Object[] args, Object target) throws Throwable;
}
