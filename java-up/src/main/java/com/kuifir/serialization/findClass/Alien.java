package com.kuifir.serialization.findClass;

import com.kuifir.serialization.findClass.xfiles.ThawAlien;

import java.io.Serializable;

/**
 * 查找对应的类：
 * 你可能想知道，如果要从序列化状态恢复一个对象，到底需要些什么。
 * 假如，假设你序列化了一个对象并将其作为文件或通过网络发送到另一台机器，那么另一台机器上的程序能否仅根据文件的内容来构建对象呢？
 * {@link FreezeAlien}{@link ThawAlien}
 *
 * @author kuifir
 * @date 2023/7/4 22:12
 */
public class Alien implements Serializable {
}
