package com.kuifir.classloading;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class ClassLoadAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Java Agent loaded!");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                // 记录加载的类名称
                System.out.println("Loaded class: " + className);
                return null; // 返回 null 表示不修改字节码
            }
        });
    }
}
