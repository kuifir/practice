package com.kuifir.automaticMemoryManagement.error.oomError.RuntimeConstantPool;

import java.util.HashSet;
import java.util.Set;

/**
 * VM args: jdk6 -XXPermSize=2M -XX:MaxPermSize=2M
 * VM args: jdk7以上 -XX:MaxMetaspaceSize=2M 会一直执行下去
 * jdk6执行 等于6M的时候没等到报错
 *
 */

public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        // set保持常量池引用，避免FULL GC回收常量池行为
        Set<String> set = new HashSet<String>();
        // 在short范围内足以让6MB的PermSize产生OOM了
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
