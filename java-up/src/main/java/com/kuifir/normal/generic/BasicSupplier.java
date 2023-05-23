package com.kuifir.normal.generic;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

/**
 * 泛型方法
 * 本类可以为任何具有无参构造器的类生成一个Supplier。
 * 为了减少代码编写量，它还包含了一个用于生成BasicSupplier的泛型方法
 *
 * @author kuifir
 * @date 2023/5/23 22:54
 */
public class BasicSupplier<T> implements Supplier<T> {
    private Class<T> type;

    public BasicSupplier(Class<T> type) {
        this.type = type;
    }

    @Override
    public T get() {
        try {
            // 假定类型是public的
            return type.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 基于类型标记生成默认的Supplier
     *
     * @param type 类型
     * @param <T> 泛型，和类的泛型无关，和类的泛型参数没有联系。
     * @return
     */
    public static <T> Supplier<T> create(Class<T> type) {
        return new BasicSupplier<>(type);
    }
}
