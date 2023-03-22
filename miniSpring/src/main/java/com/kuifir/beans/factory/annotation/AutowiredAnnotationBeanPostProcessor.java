package com.kuifir.beans.factory.annotation;

import com.kuifir.beans.BeansException;
import com.kuifir.beans.factory.config.AutowireCapableBeanFactory;
import com.kuifir.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private AutowireCapableBeanFactory beanFactory;
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                String fieldName = field.getName();
                Object autowiredObj = this.beanFactory.getBean(fieldName);
                try {
                    //设置属性值，完成注入
                    field.setAccessible(true);
                    field.set(bean, autowiredObj);
                    System.out.println("autowire " + fieldName + " for bean " + beanName);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
