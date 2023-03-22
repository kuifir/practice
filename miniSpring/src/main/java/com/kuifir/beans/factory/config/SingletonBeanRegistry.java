package com.kuifir.beans.factory.config;

public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String BeanName);

    String[] getSingletonsNames();

    void removeSingleton(String name);
}
