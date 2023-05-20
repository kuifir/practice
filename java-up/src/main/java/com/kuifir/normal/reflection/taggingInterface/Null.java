package com.kuifir.normal.reflection.taggingInterface;

/**
 * 有时候使用标签接口(tagging interface)来表示可空性更方便，
 * 标签接口没有元素，我们只是将它的名称当作标签来使用。
 * 如果你使用的是接口而不是具体类，那么就可以使用DynamicProxy来自动生成Null。
 * @see NullRobot
 * @author kuifir
 * @date 2023/5/19 23:20
 */
public interface Null {
}
