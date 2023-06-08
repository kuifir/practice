package com.kuifir.normal.collectionTopic;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 为了创建只读Map,我们要继承AbstractMap并实现entrySet方法。
 * private value()方法负责计算任意键的值，并会在get()和Entry.getValue()方法内使用。
 * CountMap的大小可以忽略不计。
 * 这里使用了LinkedHashSet,并没有创建自定义的Set类，所以并没有完全实现享元。
 * 只有当我i们调用entrySet()时，这个对象才会生成。
 *
 * @author kuifir
 * @date 2023/6/8 22:42
 */
public class CountMap extends AbstractMap<Integer, String> {
    private int size;
    private static char[] chars = "ABCDEFGHIGKLMNOPQRSTUVWXYZ".toCharArray();

    private static String value(int key) {
        return chars[key % chars.length] + Integer.toString(key / chars.length);
    }

    private CountMap(int size) {
        this.size = Math.max(size, 0);
    }

    @Override
    public String get(Object key) {
        return value((Integer) key);
    }

    private static class Entry implements Map.Entry<Integer, String> {
        int index;

        public Entry(int index) {
            this.index = index;
        }

        @Override
        public Integer getKey() {
            return index;
        }

        @Override
        public String getValue() {
            return value(index);
        }

        @Override
        public String setValue(String value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Entry && Objects.equals(index, ((Entry) obj).index);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(index);
        }
    }

    @Override
    public Set<Map.Entry<Integer, String>> entrySet() {
        // LinkedHashSet会记住初始化顺序
        return IntStream.range(0,size)
                .mapToObj(Entry::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static void main(String[] args) {
        final int size =6;
        CountMap cm = new CountMap(60);
        System.out.println(cm);
        System.out.println(cm.get(500));
        cm.values().stream()
                .limit(size)
                .forEach(System.out::println);
        System.out.println();
        new Random(47).ints(size,0,1000)
                .mapToObj(cm::get)
                .forEach(System.out::println);
    }
}
