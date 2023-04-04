package com.kuifir.future;

public interface Future<T> {
    T get(); // 获取返回结果
    boolean done(); // 判断是否完成
}
