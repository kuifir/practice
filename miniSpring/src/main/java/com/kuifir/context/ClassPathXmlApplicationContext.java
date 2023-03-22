package com.kuifir.context;

import com.kuifir.beans.*;
import com.kuifir.beans.factory.BeanFactory;
import com.kuifir.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.kuifir.beans.factory.config.AutowireCapableBeanFactory;
import com.kuifir.beans.factory.config.BeanPostProcessor;
import com.kuifir.beans.factory.support.AbstractBeanFactory;
import com.kuifir.beans.factory.support.SimpleBeanFactory;
import com.kuifir.beans.factory.xml.XmlBeanDefinitionReader;
import com.kuifir.core.ClassPathXmlResource;

import java.util.List;

public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    private AutowireCapableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.LoadBeanDefinitions(new ClassPathXmlResource(fileName));
        this.beanFactory = beanFactory;
        if (isRefresh) {
            this.refresh();
        }
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanFactoryPostProcessors() {
        return this.beanFactory.getBeanPostProcessors();
    }

    public void addBeanFactoryPostProcessor(AutowiredAnnotationBeanPostProcessor postProcessor) {
        this.beanFactory.addBeanPostProcessor(postProcessor);
    }

    private void refresh() {
        // Register bean processors that intercept bean creation.
        registerBeanPostProcessors(this.beanFactory);
        // Initialize other special beans in specific context subclasses.
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    private void onRefresh() {
        this.beanFactory.refresh();
    }

    //context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public Boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class getType(String name) {
        return null;
    }

    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerSingleton(beanName, obj);
    }

    @Override
    public void publishEvent() {

    }
}
