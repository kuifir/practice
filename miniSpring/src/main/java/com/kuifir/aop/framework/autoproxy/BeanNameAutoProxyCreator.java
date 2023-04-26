package com.kuifir.aop.framework.autoproxy;

import com.kuifir.aop.AopProxyFactory;
import com.kuifir.aop.DefaultAopProxyFactory;
import com.kuifir.aop.PointcutAdvisor;
import com.kuifir.aop.ProxyFactoryBean;
import com.kuifir.beans.BeansException;
import com.kuifir.beans.factory.BeanFactory;
import com.kuifir.beans.factory.config.BeanPostProcessor;
import com.kuifir.util.PatternMatchUtils;

public class BeanNameAutoProxyCreator<T> implements BeanPostProcessor {
    private String pattern; //代理对象名称模式，如action*
    private BeanFactory beanFactory;
    private AopProxyFactory<T> aopProxyFactory;
    private String interceptorName;
    private PointcutAdvisor advisor;

    public BeanNameAutoProxyCreator() {
        this.aopProxyFactory = new DefaultAopProxyFactory<>();
    }

    //核心方法。在bean实例化之后，init-method调用之前执行这个步骤。

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (isMatch(beanName, this.pattern)) {
            if(bean instanceof ProxyFactoryBean){
                return bean;
            }
            System.out.println(beanName + "bean name matched, " + this.pattern + " create proxy for " + bean);
            ProxyFactoryBean<T> proxyFactoryBean = new ProxyFactoryBean<>();
            //创建以恶ProxyFactoryBean
            proxyFactoryBean.setTarget((T) bean);
            proxyFactoryBean.setBeanFactory(beanFactory);
            proxyFactoryBean.setAopProxyFactory(aopProxyFactory);
            proxyFactoryBean.setInterceptorName(interceptorName);
            return proxyFactoryBean;
        } else {
            return bean;
        }
    }

    private boolean isMatch(String beanName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public AopProxyFactory<T> getAopProxyFactory() {
        return aopProxyFactory;
    }

    public void setAopProxyFactory(AopProxyFactory<T> aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    public PointcutAdvisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(PointcutAdvisor advisor) {
        this.advisor = advisor;
    }
}
