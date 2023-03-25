package com.kuifir.context;

import com.kuifir.beans.BeansException;
import com.kuifir.beans.factory.BeanFactory;
import com.kuifir.beans.factory.ListableBeanFactory;
import com.kuifir.beans.factory.config.BeanFactoryPostProcessor;
import com.kuifir.beans.factory.config.ConfigurableBeanFactory;
import com.kuifir.beans.factory.config.ConfigurableListableBeanFactory;
import com.kuifir.core.env.Environment;
import com.kuifir.core.env.EnvironmentCapable;

public interface ApplicationContext
        extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {

    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();
}
