package com.kuifir.normal.equalsHashCode;

import java.util.Map;
import java.util.Objects;

/**
 * @author kuifir
 * @date 2023/7/3 21:43
 */
public class MapEntry<K, V> implements Map.Entry {
    private K key;
    private V value;

    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Object getKey() {
        return key;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public V setValue(Object v) {
        V result = value;
        value = (V) v;
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object rval) {
        return rval instanceof MapEntry other
                && Objects.equals(key, ((MapEntry<K, V>) other).getKey())
                && Objects.equals(value, ((MapEntry<K, V>) other).getValue());
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
