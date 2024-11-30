package com.kuifir.remote.execute;

/**
 * 多次载入执行类而加入的类加载器
 */
public class HotSwapClassLoader extends ClassLoader{
    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class<?> loadBytes(byte[] classByte){
        return defineClass(null,classByte,0,classByte.length);
    }
}
