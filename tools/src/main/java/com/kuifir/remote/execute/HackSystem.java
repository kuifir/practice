package com.kuifir.remote.execute;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;

public class HackSystem {
    public final static InputStream in = System.in;
    private static final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public final static PrintStream out = new PrintStream(buffer);

    public final static PrintStream err = out;

    public static String getBufferString(){
        return buffer.toString();
    }

    public static void clearBuffer(){
        buffer.reset();
    }
    public static String getProperty(String key) {
        return System.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return System.getProperty(key, defaultValue);
    }

    public static String setProperty(String key, String value) {
        return System.setProperty(key, value);
    }

    public static Properties getProperties() {
        return System.getProperties();
    }

    public static void setProperties(Properties props) {
        System.setProperties(props);
    }

    public static String clearProperty(String key) {
        return System.clearProperty(key);
    }

    // 环境变量相关
    public static Map<String, String> getenv() {
        return System.getenv();
    }

    public static String getenv(String name) {
        return System.getenv(name);
    }

    // 当前时间相关
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long nanoTime() {
        return System.nanoTime();
    }

    // GC 调用
    public static void gc() {
        System.gc();
    }

    public static void runFinalization() {
        System.runFinalization();
    }

    // 加载类、库
    public static void load(String filename) {
        System.load(filename);
    }

    public static void loadLibrary(String libname) {
        System.loadLibrary(libname);
    }

    public static String mapLibraryName(String libname) {
        return System.mapLibraryName(libname);
    }

    // Console
    public static Console console() {
        return System.console();
    }

    // 数组拷贝
    public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
    }

    // 退出相关
    public static void exit(int status) {
        System.exit(status);
    }


    // 标识符相关
    public static int identityHashCode(Object x) {
        return System.identityHashCode(x);
    }

    // 文件编码设置
    public static String lineSeparator() {
        return System.lineSeparator();
    }

    public static void setDefaultCharset(String charset) {
        System.setProperty("file.encoding", charset);
    }
}
