package com.kuifir.beans.factory;

import com.kuifir.beans.BeansException;
import com.kuifir.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.kuifir.beans.factory.config.BeanDefinition;
import com.kuifir.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DefaultListableBeanFactory
        extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {

    ConfigurableListableBeanFactory parentBeanFactory;


    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(new String[0]);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();

        for (String beanName : this.beanDefinitionNames) {
            boolean matchFound = false;
            BeanDefinition mbd = this.getBeanDefinition(beanName);
            Class<?> classToMatch = null;
            try {
                classToMatch = Class.forName(mbd.getClassName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            matchFound = type.isAssignableFrom(classToMatch);

            if (matchFound) {
                result.add(beanName);
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);
        }
        return result;
    }

    public void setParent(ConfigurableListableBeanFactory beanFactory) {
        this.parentBeanFactory = beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException{
        Object result = super.getBean(beanName);
        if (result == null && null!= this.parentBeanFactory) {
            result = this.parentBeanFactory.getBean(beanName);
        }
        return result;
    }


    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {
        // todo
    }

    @Override
    public String[] getDependentBeans(String beanName) {
        // todo
        return new String[0];
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        // todo
        return new String[0];
    }
}
