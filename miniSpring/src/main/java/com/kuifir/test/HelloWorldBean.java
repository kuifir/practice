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
    public Date param(Test2Param test2Param) {
        System.out.println("date　：" + test2Param.getDate().toString());
        return test2Param.getDate();
    }

    public String doPost() {
        return "hello world for doPost!";
    }
}
