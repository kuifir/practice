package com.kuifir.test;

import com.kuifir.batis.SqlSession;
import com.kuifir.batis.SqlSessionFactory;
import com.kuifir.beans.factory.annotation.Autowired;
import com.kuifir.jdbc.core.JDBCTemplate;
import com.kuifir.web.RequestMapping;
import com.kuifir.web.bind.annotation.ResponseBody;
import com.kuifir.web.servlet.ModelAndView;

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
}
