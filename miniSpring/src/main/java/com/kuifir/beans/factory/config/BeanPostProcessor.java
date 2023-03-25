package com.kuifir.beans.factory.config;

import com.kuifir.beans.BeansException;
import com.kuifir.beans.factory.BeanFactory;

public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

    void setBeanFactory(BeanFactory beanFactory);
}
