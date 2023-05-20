package com.kuifir.normal.reflection.taggingInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * 每当需要一个空的Robot对象时，调用bewNullRobot()即可，传递给他想要的Robot,它会返回一个代理。
 * 代理会同时满足Robot和Null接口的要求，并提供她所代理的类型的特定名称。
 * @author kuifir
 * @date 2023/5/19 23:51
 */
public class NullRobot {
    public static Robot newNullRoBot(Class<? extends Robot> type) {
        return (Robot) Proxy.newProxyInstance(NullRobot.class.getClassLoader(),
                new Class[]{Null.class, Robot.class},
                new NullRobotProxyHandler(type));
    }

    public static void main(String[] args) {
        Stream.of(new SnowRobot("SnowBee"),
                newNullRoBot(SnowRobot.class)
        ).forEach(Robot::test);
    }
}

class NullRobotProxyHandler implements InvocationHandler {
    private String nullName;
    private Robot proxied = new NRobot();

    public NullRobotProxyHandler(Class<? extends Robot> type) {
        nullName = type.getSimpleName() + " NullRobot";
    }

    private class NRobot implements Null, Robot {

        @Override
        public String name() {
            return nullName;
        }

        @Override
        public String model() {
            return nullName;
        }

        @Override
        public List<Operation> operations() {
            return Collections.emptyList();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxied, args);
    }
}