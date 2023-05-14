package com.kuifir.normal.initialization;

/**
 * 可以通过方法来提供初始值；// int i = f();
 * 这个方法可以有参数，但是这些参数必须是已经初始化了的； int j = g(i);
 *
 * @author kuifir
 * @date 2023/5/14 13:08
 */
public class MethodInit {
    /**
     * 不可以写在这会编译错误：Illegal forward reference 非法的向前引用。这与初始化顺序有关。
     * int j = g(i);
     */
    int i = f();
    int j = g(i);

    int f() {
        return 11;
    }

    int g(int n) {
        return n * 10;
    }
}
