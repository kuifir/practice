package com.kuifir.test;

import com.kuifir.beans.BeansException;
import com.kuifir.context.ClassPathXmlApplicationContext;

public class Test1 {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("META-INF/beans.xml");
        AService aservice = applicationContext.getBean("aservice", AService.class);
        aservice.sayHello();
    }
}
