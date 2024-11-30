package com.kuifir.remote.execute;

import java.lang.reflect.Method;

public class JavaClassExecuter {

    public static String execute(byte[] classBytes) {
        HackSystem.clearBuffer();
        ClassModfier classModfier = new ClassModfier(classBytes);
        byte[] modifyClassBytes = classModfier.midifyUTF8Constant("java/lang/System","com/kuifir/remote/execute/HackSystem");

        HotSwapClassLoader hotSwapClassLoader = new HotSwapClassLoader();
        Class<?> aClass = hotSwapClassLoader.loadBytes(modifyClassBytes);
        try {
            Method method = aClass.getMethod("main", String[].class);
            method.invoke(null, new String[]{null});
        } catch (Throwable e) {
            e.printStackTrace(HackSystem.err);
        }
        return HackSystem.getBufferString();

    }
}
