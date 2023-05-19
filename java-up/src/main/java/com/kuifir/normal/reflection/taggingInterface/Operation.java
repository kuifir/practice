package com.kuifir.normal.reflection.taggingInterface;

import java.util.function.Supplier;

/**
 * 包含一个描述和一个命令[这是有一种命令模式（Command pattern）].
 * 它们被定义为对函数式接口的引用，这样你就可以将lambda表达式或者方法引用传递给Operation的构造器。
 *
 * @author kuifir
 * @date 2023/5/19 23:33
 */
public record Operation(Supplier<String> description, Runnable command) {
}
