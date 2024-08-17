package com.kuifir.algorithm.LRU;

import com.kuifir.table.linked.DoublyLinkList;

import java.util.Objects;

/**
 * 基于自定义双向链表
 * @param <K>
 * @param <V>
 */

public class LinkedListLRU<K,V> {
    public static void main(String[] args) {
        LinkedListLRU<Integer,Integer> lRUCache = new LinkedListLRU<>(2);
        lRUCache.put(1,1);
//        System.out.println(lRUCache.list);
        lRUCache.put(2,2);
//        System.out.println(lRUCache.list);
        System.out.println(lRUCache.get(1));
        lRUCache.put(3,3);
        System.out.println(lRUCache.get(2));
        lRUCache.put(4,4);
        System.out.println(lRUCache.get(1));
        System.out.println(lRUCache.get(3));
        System.out.println(lRUCache.get(4));
        System.out.println(lRUCache.list);

    }
    private int capacity;
    private DoublyLinkList<Pari<K,V>> list = new DoublyLinkList<>();

    public LinkedListLRU(int capacity) {
        this.capacity = capacity;
    }

    public V get(K key) {
        Pari<K, V> e = new Pari<>(key);
        int i = list.locateItem(e);
        if(i != -1){
            Pari<K, V> item = list.findNode(e).getItem();
            list.listDelete(i);
            list.listInsert(1,item);
            return item.v;
        }
        return null;
    }
    public void put(K key, V value) {
        Pari<K, V> e = new Pari<>(key, value);
        int i = list.locateItem(e);
        if(i != -1){
            list.listDelete(i);
            list.listInsert(1,e);
        }else {
            if(list.getSize() < capacity){
                list.listInsert(1,e);
            }else {
                list.listDelete(list.getSize());
                list.listInsert(1,e);
            }
        }
    }
    class Pari<K,V> {
        private K k;
        private V v;

        public Pari(K k) {
            this.k = k;
        }

        public Pari(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pari<?, ?> pari = (Pari<?, ?>) o;
            return Objects.equals(k, pari.k);
        }

        @Override
        public int hashCode() {
            return Objects.hash(k);
        }

        @Override
        public String toString() {
            return "Pari{" +
                    "k=" + k +
                    ", v=" + v +
                    '}';
        }
    }
}
