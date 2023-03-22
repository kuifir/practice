package com.kuifir.test.impl;

import com.kuifir.beans.factory.annotation.Autowired;
import com.kuifir.test.AService;
import com.kuifir.test.BaseService;

public class AServiceImpl implements AService {
    private String name;
    private int level;
    private String property1;
    private String property2;
    private BaseService baseService;
    private BaseService bbs;

    @Autowired
    private BaseService baseserviceAnnotation;

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public BaseService getBaseService() {
        return baseService;
    }

    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }


    @Override
    public void sayHello() {
        System.out.println("a service say hello");
    }

    @Override
    public String toString() {
        return "AServiceImpl{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                ", baseService=" + baseService +
                ", bbs=" + bbs +
                ", baseserviceAnnotation=" + baseserviceAnnotation +
                '}';
    }

    public AServiceImpl(String name, int level, BaseService bbs) {
        this(name,level);
        this.bbs = bbs;
    }

}
