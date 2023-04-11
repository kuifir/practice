package com.kuifir.test;

import com.kuifir.beans.factory.annotation.Autowired;
import com.kuifir.web.RequestMapping;
import com.kuifir.web.bind.annotation.ResponseBody;
import com.kuifir.web.servlet.ModelAndView;

import java.util.Date;

public class HelloWorldBean {

    @Autowired
    private BaseService baseserviceAnnotation;

    @RequestMapping("/test")
    @ResponseBody
    public String doGet() {
        return baseserviceAnnotation.toString();
    }

    @RequestMapping("/test2")
    @ResponseBody
    public Date param(TestParam testParam) {
        System.out.println("date　：" + testParam.getDate().toString());
        System.out.println("number　：" + testParam.getNumber().toString());
        return testParam.getDate();
    }

    @RequestMapping("/test3")
    @ResponseBody
    public TestParam doTest3(TestParam testParam) {
        testParam.setName(testParam.getName() + "---");
        testParam.setDate(new Date());
        return testParam;
    }
    @RequestMapping("/error")
    public String errorPAge() {
        return "error";
    }
    @RequestMapping("/test/modelAndView")
    public ModelAndView testPageWithModel() {
        return new ModelAndView("test","msg", "测试成功");
    }
}
