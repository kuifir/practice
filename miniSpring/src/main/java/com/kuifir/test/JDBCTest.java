package com.kuifir.test;

import com.kuifir.batis.SqlSession;
import com.kuifir.batis.SqlSessionFactory;
import com.kuifir.beans.BeansException;
import com.kuifir.context.ClassPathXmlApplicationContext;
import com.kuifir.jdbc.core.JDBCTemplate;
import com.kuifir.jdbc.core.JDBCTemplateWithoutFuntion;
import com.kuifir.jdbc.core.RowMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class JDBCTest {
    public static final String QUERYSQL = "select * from user ";
    public static final String CONDITION = " where id = ? ";
    public static final String LIMIT = " limit 3 ";

    private static JDBCTemplate jdbcTemplate = null;

    private static SqlSessionFactory sqlSessionFactory = null;

    @BeforeAll
    public static void init() throws BeansException {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("META-INF/beans.xml");
        jdbcTemplate = (JDBCTemplate) applicationContext.getBean("jdbcTemplate");
        sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
    }

    @Disabled
    public static <T extends Statement> Object apply(String sql, T statement) {
        ResultSet resultSet = null;
        try {
            if (statement instanceof PreparedStatement statement1) {
                resultSet = statement1.executeQuery();
            } else {
                resultSet = statement.executeQuery(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<User> userList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = createUser(resultSet);
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Disabled
    public static User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setBirthday(resultSet.getDate("birthday"));
        user.setSex(resultSet.getInt("sex"));
        user.setAddress(resultSet.getString("address"));
        return user;
    }

    @Test
    public void jdbcConnection() {
        String url = "jdbc:mysql://localhost:3306/mybatis";
        String username = "root";
        String password = "123456";
        // 注册驱动
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(QUERYSQL + LIMIT);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (null == connection) {
                System.out.println("连接失败");
                return;
            }
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setSex(resultSet.getInt("sex"));
                user.setAddress(resultSet.getString("address"));
                users.add(user);
            }
            users.forEach(System.out::println);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void jdbcTemplateWithoutFunction() {
        JDBCTemplateWithoutFuntion jdbcTemplate = new UserWithoutFuntionJdbcImpl();
        List<User> users = (List<User>) jdbcTemplate.query(QUERYSQL + LIMIT);
        users.forEach(System.out::println);
    }

    @Test
    public void jdbcTemplateStatement() {
        List<User> users = (List<User>) jdbcTemplate.query(statement -> apply(QUERYSQL + LIMIT, statement));
        users.forEach(System.out::println);
    }

    @Test
    public void jdbcTemplatePrepareStatement() {
        System.out.println(QUERYSQL + CONDITION + LIMIT);
        List<User> users = (List<User>) jdbcTemplate.query(QUERYSQL + CONDITION + LIMIT, new Object[]{10},
                statement -> apply(null, statement));
        users.forEach(System.out::println);
    }

    @Test
    public void jdbcTemplatePrepareStatementWithRowMapper() {
        System.out.println(QUERYSQL + CONDITION + LIMIT);
        List<User> users = jdbcTemplate.queryList(QUERYSQL + CONDITION + LIMIT, new Object[]{10},
                (resultSet, rowNum) -> createUser(resultSet));
        users.forEach(System.out::println);
    }

    @Test
    public void jdbcTemplatePrepareStatementWithRowMapperThread() {
        System.out.println(QUERYSQL + CONDITION + LIMIT);
        List<CompletableFuture<Void>> completableFutures = IntStream.range(0, 1000)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    List<User> users = jdbcTemplate.queryList(QUERYSQL + CONDITION + LIMIT, new Object[]{10},
                            (resultSet, rowNum) -> createUser(resultSet));
                    users.forEach(System.out::println);
                }))
                .collect(Collectors.toList());
        completableFutures.forEach(CompletableFuture::join);
    }

    @Test
    public void batisTest() {
        String sqlid = "com.kuifir.test.HelloWorldBean.testBatis";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne(sqlid,
                new Object[]{Integer.valueOf(10)},
                (rs, rowNum) -> createUser(rs));
        System.out.println(user);

    }
}
