package com.kuifir.algorithm.LRU;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于hashMap
 */

public class LinkedHashMapLRU extends LinkedHashMap<Integer, Integer> {

    public static void main(String[] args) {
        LinkedHashMapLRU lRUCache = new LinkedHashMapLRU(2);
        lRUCache.put(1, 1);
//        System.out.println(lRUCache.list);
        lRUCache.put(2, 2);
//        System.out.println(lRUCache.list);
        System.out.println(lRUCache.get(1));
        lRUCache.put(3, 3);
        System.out.println(lRUCache.get(2));
        lRUCache.put(4, 4);
        System.out.println(lRUCache.get(1));
        System.out.println(lRUCache.get(4));
        System.out.println(lRUCache.get(3));
        System.out.println(lRUCache);
    }

    private int capacity;

    public LinkedHashMapLRU(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
