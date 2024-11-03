package com.kuifir.automaticMemoryManagement;

/**
 * jdk7之前版本和之后版本分别运行
 * jdk 6 false false
 * jdk7 true false
 */
public class RuntimeConstantPoolOOMTest {
    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
