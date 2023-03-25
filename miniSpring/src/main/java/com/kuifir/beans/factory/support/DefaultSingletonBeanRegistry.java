package com.kuifir.beans.factory.support;

import com.kuifir.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    // 容器存放所有bean的名称列表
    protected List<String> beanNames = new ArrayList<>();
    // 容器中存放所有bean实例的map
    private Map<String,Object> singletons = new ConcurrentHashMap<>(256);
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (singletons) {
            this.singletons.put(beanName,singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String BeanName) {
        return this.singletons.containsKey(beanNames);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }

    @Override
    public void removeSingleton(String name) {
        singletons.remove(name);
    }
}
