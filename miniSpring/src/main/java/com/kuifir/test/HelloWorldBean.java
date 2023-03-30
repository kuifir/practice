package com.kuifir.test;

import com.kuifir.web.RequestMapping;

public class HelloWorldBean {

    @RequestMapping("/test")
    public String doGet() {
        return "hello world for doGet!";
    }

    public String doPost() {
        return "hello world for doPost!";
    }
}
