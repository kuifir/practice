package com.kuifir.normal.collectionTopic;

import java.util.*;

/**
 * 现在我们创建一个更复杂的享元。
 * 在这个示例中，数据集是由国家及其首都组成的Map,capitals()方法可以生成这样的Map.
 * names()方法则会生成包含国家名的List。
 * 这两个方法都可以通过一个指定了所需大小的int参数来获得由部分元素组成的列表。
 *
 * @author kuifir
 * @date 2023/6/8 23:19
 */
public class Countries {
    public static final String[][] DATA = {
            // 非洲
            {"ALGERIA", "Algiers"},
            // 亚洲
            {"AFHANISTAN", "kabul"},
            // 大洋洲
            {"AUSTRALIA", "Canberra"},
            // 欧洲
            {"ALBANIA", "Tirana"},
            // 中北美洲
            {"ANTIGUA AND BARBUDA", "Saint John's"},
            // 南美洲
            {"ARGENTINA", "Buenos Aires"}
    };

    /**
     * 通过实现entrySet()来使用AbstractMap
     */
    private static class FlyweightMap extends AbstractMap<String, String> {
        private static Set<Map.Entry<String, String>> entries = new EntrySet(DATA.length);

        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return entries;
        }

        private static class Entry implements Map.Entry<String, String> {
            int index;

            Entry(int index) {
                this.index = index;
            }

            @Override
            public String getKey() {
                return DATA[index][0];
            }

            @Override
            public String getValue() {
                return DATA[index][1];
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean equals(Object o) {
                return o instanceof FlyweightMap && Objects.equals(DATA[index][0], o);
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(DATA[index][0]);
            }
        }

        /**
         * 实现 AbstractSet的size()和iterator()方法
         */
        static class EntrySet extends AbstractSet<Map.Entry<String, String>> {
            private int size;

            public EntrySet(int size) {
                if (size < 0) {
                    this.size = 0;
                } else {
                    this.size = Math.min(size, DATA.length);
                }
            }

            @Override
            public int size() {
                return size;
            }

            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return new Iner();
            }

            private class Iner implements Iterator<Map.Entry<String, String>> {
                /**
                 * 每个Iterator只有一个Entry对象：
                 */
                private Entry entry = new Entry(-1);

                @Override
                public boolean hasNext() {
                    return entry.index < size - 1;
                }

                @Override
                public Map.Entry<String, String> next() {
                    entry.index++;
                    return entry;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            }

        }

    }

    /**
     * 创建一个由'size'个国家组成的部分映射
     */
    static Map<String, String> select(final int size) {
        return new FlyweightMap() {
            @Override
            public Set<Map.Entry<String, String>> entrySet() {
                return new EntrySet(size);
            }
        };
    }
    static Map<String,String>map = new FlyweightMap();
    public static Map<String,String> capitals(){
        // 整个映射
        return map;
    }
    public static Map<String,String> capitals(int size){
        // 部分映射
        return select(size);
    }
    static List<String> names = new ArrayList<>(map.keySet());
    public static List<String> names(){
        return names;
    }
    public static List<String> names(int size){
        // 部分列表
        return new ArrayList<>(select(size).keySet());
    }

    public static void main(String[] args) {
        System.out.println(capitals(5));
        System.out.println(names(5));
        System.out.println(new HashMap<>(capitals(3)));
        System.out.println(new LinkedHashMap<>(capitals(3)));
        System.out.println(new TreeMap<>(capitals(3)));
        System.out.println(new Hashtable<>(capitals(3)));
        System.out.println(new LinkedHashSet<>(names(3)));
        System.out.println(new TreeSet<>(names(3)));
        System.out.println(new ArrayList<>(names(3)));
        System.out.println(new LinkedList<>(names(3)));
        System.out.println(capitals().get("ARGENTINA"));

    }
}
