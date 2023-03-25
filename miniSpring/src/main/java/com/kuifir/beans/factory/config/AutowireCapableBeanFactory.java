package com.kuifir.beans.factory.config;

import com.kuifir.beans.BeansException;
import com.kuifir.beans.factory.BeanFactory;
import com.kuifir.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.kuifir.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

public interface AutowireCapableBeanFactory extends BeanFactory {
    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
