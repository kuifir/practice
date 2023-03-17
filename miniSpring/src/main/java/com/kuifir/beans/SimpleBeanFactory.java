package com.kuifir.beans;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleBeanFactory implements BeanFactory {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();


    //getBean，容器的核心方法
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿Bean实例
        Object singleton = singletons.get(beanName);
        //如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        if (null == singleton) {
            int i = beanNames.indexOf(beanName);
            if (i == -1) {
                throw new BeansException();
            } else {
                BeanDefinition beanDefinition = beanDefinitions.get(i);
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance();
                } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                         InvocationTargetException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                singletons.put(beanDefinition.getId(), singleton);
            }
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanNames.add(beanDefinition.getId());
        this.beanDefinitions.add(beanDefinition);
    }

}
