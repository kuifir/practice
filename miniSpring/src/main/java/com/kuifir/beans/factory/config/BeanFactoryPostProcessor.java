package com.kuifir.beans.factory.config;

import com.kuifir.beans.BeansException;
import com.kuifir.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
