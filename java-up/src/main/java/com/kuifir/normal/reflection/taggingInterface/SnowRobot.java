package com.kuifir.normal.reflection.taggingInterface;

import java.util.Arrays;
import java.util.List;

/**
 * 有扫雪功能的机器人
 * @see Robot
 * @author kuifir
 * @date 2023/5/19 23:39
 */
public class SnowRobot implements Robot {
    private String name;
    private List<Operation> ops = Arrays.asList(
            new Operation(
                    () -> name + " can shovel snow",
                    () -> System.out.println(name + " shoveling snow")),
            new Operation(
                    () -> name + " can chip snow",
                    () -> System.out.println(name + " chipping snow")),
            new Operation(
                    () -> name + " can clear the roof",
                    () -> System.out.println(name + " cleaning roof"))
    );

    public SnowRobot(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String model() {
        return "SnowBot Series 11";
    }

    @Override
    public List<Operation> operations() {
        return ops;
    }

    public static void main(String[] args) {
        Robot.test(new SnowRobot("Slusher"));
    }
}
