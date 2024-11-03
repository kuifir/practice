package com.kuifir.automaticMemoryManagement.error.oomError.methodArea;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * VM Args: java 8之前 -XX:PermSize=10M -XX:MaxPermSize=10M
 * java8 -XX:MaxMetaspaceSize=10M -XX:MetaspaceSize=10M
 * java9之后 -XX:MaxMetaspaceSize=10M -XX:MetaspaceSize=10M --add-opens java.base/java.lang=ALL-UNNAMED
 */

public class JavaMethodAreaOOM {
    static class OOMObject {

    }

    public static void main(String[] args) {
        while (true) {
            Enhancer enHancer = new Enhancer();
            enHancer.setSuperclass(OOMObject.class);
            enHancer.setUseCache(false);
            enHancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            enHancer.create();
        }
    }
}
