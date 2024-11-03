package com.kuifir.automaticMemoryManagement.error.stackOFError.stackLeak;

/**
 * VM args -Xss180k
 * 使用-Xss参数减少栈内存容量，异常出现时输出的堆栈深度相应缩小
 */
public class JavaVMStackSOF {
    private static int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }
    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + stackLength);
            throw new RuntimeException(e);
        }
    }
}
