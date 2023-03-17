package com.kuifir.beans;

import com.kuifir.test.AService;

public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);

    default <T> T getBean(String beanName, Class<T> clazz) throws BeansException {
        return clazz.cast(getBean(beanName));
    }
}
