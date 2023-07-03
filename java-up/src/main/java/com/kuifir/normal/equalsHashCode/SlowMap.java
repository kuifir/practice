package com.kuifir.normal.equalsHashCode;

import com.kuifir.normal.collectionTopic.Countries;

import java.util.*;

/**
 * 用ArrayList实现的Map
 *
 * @author kuifir
 * @date 2023/7/3 21:20
 */
public class SlowMap<K, V> extends AbstractMap<K, V> {
    private List<K> keys = new ArrayList<>();
    private List<V> values = new ArrayList<>();

    @Override
    public V put(K key, V value) {
        // 旧值或null
        V oldValue = get(key);
        if (!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        } else {
            values.set(keys.indexOf(key), value);
        }
        return oldValue;
    }

    @Override
    public V get(Object key) {
        // key时Object类型，而非参数化类型K
        if (!keys.contains(key)) {
            return null;
        }
        return values.get(keys.indexOf(key));
    }

    /**
     * 这不是一个正确的实现，因为它复制了键和值。
     * entrySet()的正确实现需要提供一个Map的视图，而不是一个副本，并且这个视图允许我们修改原始的Map(副本则不行)
     * @return
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        Iterator<K> ki = keys.iterator();
        Iterator<V> vi = values.iterator();
        while (ki.hasNext()) {
            set.add(new MapEntry(ki.next(), vi.next()));
        }
        return set;
    }

    public static void main(String[] args) {
        SlowMap<String, String> m = new SlowMap<>();
        m.putAll(Countries.capitals(8));
        m.forEach((k, v) -> System.out.println(k + "=" + v));
        System.out.println(m.get("ARGENTINA"));
        m.entrySet().forEach(System.out::println);
    }
}
