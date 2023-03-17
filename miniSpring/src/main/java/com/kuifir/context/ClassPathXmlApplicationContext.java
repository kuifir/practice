package com.kuifir.context;

import com.kuifir.beans.*;
import com.kuifir.core.ClassPathXmlResource;

public class ClassPathXmlApplicationContext implements BeanFactory {
   private BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.LoadBeanDefinition(new ClassPathXmlResource(fileName));
        this.beanFactory = beanFactory;
    }

    //context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }
    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }

}
