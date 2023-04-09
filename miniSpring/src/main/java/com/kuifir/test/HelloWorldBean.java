package com.kuifir.test;

import com.kuifir.beans.factory.annotation.Autowired;
import com.kuifir.web.RequestMapping;

import java.util.Date;

public class HelloWorldBean {

    @Autowired
    private BaseService baseserviceAnnotation;
    @RequestMapping("/test")
    public String doGet() {
        return baseserviceAnnotation.toString();
    }
    @RequestMapping("/test2")
    public Date param(Date date) {
        System.out.println("date　：" + date.toString());
        return date;
    }

    public String doPost() {
        return "hello world for doPost!";
    }
}
