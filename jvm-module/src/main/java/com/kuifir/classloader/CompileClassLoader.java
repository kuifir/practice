package com.kuifir.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义ClassLoader,通过重写 {@link ClassLoader#findClass(String name)}方法来实现自定义的类加载机制。
 * 这个ClassLoader可以在加载类之前先编译该类的源文件，从而实现 运行Java之前先编译该程序 的目标，
 * 这样即可通过该ClassLoader直接运行Java源文件。
 *
 * @author kuifir
 * @date 2023/10/9 23:11
 */
public class CompileClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        String fileStub = name.replace('.', '/');
        String javaFileName = fileStub + ".java";
        String classFileName = fileStub + ".class";
        File javaFile = new File(javaFileName);
        File classFile = new File(classFileName);
        // 当指定Java源文件存在，且Class文件不存在，或者Java源文件的修改时间比Class文件的修改时间更晚时，重新编译
        if (javaFile.exists() && (!classFile.exists() || javaFile.lastModified() > classFile.lastModified())) {
            try {
                // 如果编译失败，或者该Class文件不存在
                if (!compile(javaFileName) || !classFile.exists()) {
                    throw new ClassNotFoundException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 如果class文件存在，系统负责将该文件转成Class对象
        if (classFile.exists()) {
            try {
                // 将Class文件的二进制数据读入数组
                byte[] raw = getBytes(classFileName);
                // 调用ClassLoader的defineClass方法将二进制数据转成Class对象
                clazz = defineClass(name, raw, 0, raw.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 如果clazz为null,表明加载失败，则抛出异常
        if (clazz == null) {
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }

    /**
     * 读取一个文件的内容
     */
    private byte[] getBytes(String fileName) throws IOException {

        File file = new File(fileName);
        long len = file.length();
        byte[] raw = new byte[(int) len];
        try (
                FileInputStream fin = new FileInputStream(file)
        ) {
            // 一次性读取Class文件的全部二进制数据
            int r = fin.read(raw);
            if (r != len) {
                throw new IOException("无法读取全部文件： " + r + " != " + len);
            }
            return raw;
        }

    }

    /**
     * 定义编译制定Java文件的方法
     *
     * @param javaFile 类全限定名
     * @return
     */
    private boolean compile(String javaFile) throws IOException {
        System.out.println("CompileClassLoader 正在编译 " + javaFile + "....");
        // 调用系统javac 的命令
        Process p = Runtime.getRuntime().exec("javac " + javaFile);
        try {
            // 其他线程都在等待这个线程完成
            p.waitFor();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        // 获取javac线程的退出值
        int ret = p.exitValue();
        // 返回编译是否成功
        return ret == 0;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        CompileClassLoader compileClassLoader = new CompileClassLoader();
        Class<?> aClass = compileClassLoader.loadClass("com.kuifir.classloader.SampleClass");
        System.out.println(aClass.getName());
        System.out.println(aClass.getClassLoader());
    }
}
