package com.kuifir.beans;

import com.kuifir.test.AService;

public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    Boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class getType(String name);

    default <T> T getBean(String beanName, Class<T> clazz) throws BeansException {
        return clazz.cast(getBean(beanName));
    }
}
