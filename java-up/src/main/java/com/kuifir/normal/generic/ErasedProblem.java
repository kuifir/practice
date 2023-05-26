package com.kuifir.normal.generic;

/**
 * 泛型擦除的缘故，我们失去了在泛型代码中执行某些操作的能力。
 * 任何在运行时知道确切类型的操作都无法运行：
 * 1. instanceof
 * 2. 创建泛型类型实例
 * 3. 创建泛型数组
 * 4. 泛型数组强转
 *
 * @author kuifir
 * @date 2023/5/26 23:30
 */
public class ErasedProblem<T> {
    private final int SIZE = 100;

    public void f(Object arg) {
        // error:　illegal generic type for instanceof java: java.lang.Object 无法安全地转换为 T
        // if(arg instanceof T){}
        // error:　unexpected type 意外的类型
        // T var = new T();
        // error:　generic array creation
        // T[] array = new T[SIZE];
        // waring: Unchecked cast: 'java.lang.Object[]' to 'T[]'
        T[] array = (T[])new Object[SIZE];
    }

    public static void main(String[] args) {
        ErasedProblem<Integer> e = new ErasedProblem<>();
        e.f(1);
    }
}
