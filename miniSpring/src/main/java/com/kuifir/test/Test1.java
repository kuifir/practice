package com.kuifir.test;

import com.kuifir.beans.BeansException;
import com.kuifir.context.ClassPathXmlApplicationContext;
import com.kuifir.test.impl.AServiceImpl;

public class Test1 {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("META-INF/beans.xml");
        AService aservice = applicationContext.getBean("aservice", AServiceImpl.class);
        aservice.sayHello();
        System.out.println(aservice);
    }
}
