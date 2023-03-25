package com.kuifir.beans.factory;

import com.kuifir.beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory{

    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
