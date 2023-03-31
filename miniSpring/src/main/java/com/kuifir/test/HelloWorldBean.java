package com.kuifir.test;

import com.kuifir.beans.factory.annotation.Autowired;
import com.kuifir.web.RequestMapping;

public class HelloWorldBean {

    @Autowired
    private BaseService baseserviceAnnotation;
    @RequestMapping("/test")
    public String doGet() {
        return baseserviceAnnotation.toString();
    }

    public String doPost() {
        return "hello world for doPost!";
    }
}
