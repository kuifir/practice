package com.kuifir.test;

import com.kuifir.batis.SqlSession;
import com.kuifir.batis.SqlSessionFactory;
import com.kuifir.beans.factory.annotation.Autowired;
import com.kuifir.jdbc.core.JDBCTemplate;
import com.kuifir.test.service.DynamicProxy;
import com.kuifir.test.service.IAction;
import com.kuifir.web.RequestMapping;
import com.kuifir.web.bind.annotation.ResponseBody;
import com.kuifir.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.kuifir.test.JDBCTest.*;

public class HelloWorldBean {

    @Autowired
    private BaseService baseserviceAnnotation;

    @Autowired
    private JDBCTemplate jdbcTemplate;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private IAction action;
    @Autowired
    private IAction action1;
    @Autowired
    private IAction action2;

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
        return new ModelAndView("test", "msg", "测试成功");
    }

    @RequestMapping("/test/jdbcTemplate")
    @ResponseBody
    public User test() {
        List<User> users = (List<User>) jdbcTemplate.query(QUERYSQL + CONDITION + LIMIT, new Object[]{Integer.valueOf(10)},
                statement -> apply(null, statement));
        return users.get(0);
    }

    @RequestMapping("/test/batis")
    @ResponseBody
    public User testBatis() {
        String sqlid = "com.kuifir.test.HelloWorldBean.testBatis";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.selectOne(sqlid,
                new Object[]{Integer.valueOf(10)},
                (rs, rowNum) -> createUser(rs));
    }

    @RequestMapping("/testaop")
    public void doTestAop(HttpServletRequest request, HttpServletResponse response) {
        DynamicProxy proxy = new DynamicProxy(action);
        IAction action = (IAction) proxy.getProxy();
        action.doAction();
        String str = "test aop, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/testaop2")
    public void doTestJDKDynamicAop(HttpServletRequest request, HttpServletResponse response) {
        action.doAction();
        String str = "test aop, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/testaop3")
    public void doTestJDKDynamicAop2(HttpServletRequest request, HttpServletResponse response) {
        action.doSomething();
        String str = "test aop, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/testaop4")
    public void doTestJDKDynamicAutoProxy(HttpServletRequest request, HttpServletResponse response) {
        action1.doAction();
        action2.doSomething();
        String str = "test aop, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
