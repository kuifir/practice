package com.kuifir.aop;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
