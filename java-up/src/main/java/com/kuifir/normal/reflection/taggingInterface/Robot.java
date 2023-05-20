package com.kuifir.normal.reflection.taggingInterface;

import java.util.List;

/**
 * 如果你使用的是接口而不是具体类，那么就可以使用DynamicProxy来自动生成{@link Null}。{@link NullRobot}
 * 假设有个Robot接口，它定义了名称、模型以及一个描述了自身功能的{@code List<Operation>}
 *
 * 空Robot {@link NullRobot}
 * 扫雪机器人 {@link SnowRobot}
 * @author kuifir
 * @date 2023/5/19 23:23
 * @see Null
 * @see NullRobot
 */
public interface Robot {
    /**
     * 描述名称
     * @return
     */
    String name();

    /**
     * 描述模型
      * @return
     */
    String model();

    /**
     * 描述自身功能，可以通过调用方法来访问Robot的服务
     * @return
     */
    List<Operation> operations();

    /**
     * 测试
     * @param r
     */
    static void test(Robot r){
        if (r instanceof Null){
            System.out.println("[Null Robot]");
        }
        System.out.println("Robot name " + r.name());
        System.out.println("Robot model " + r.model());
        for (Operation operation : r.operations()) {
            System.out.println(operation.description().get());
            operation.command().run();
        }
    }
}
