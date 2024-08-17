package com.kuifir.algorithm.LRU;

import java.util.HashMap;
import java.util.Map;

public class HashMapLinkedListLRU<K, V> {
    public static void main(String[] args) {
        HashMapLinkedListLRU<Integer, Integer> lRUCache = new HashMapLinkedListLRU<>(2);
        lRUCache.put(1, 1);
//        System.out.println(lRUCache.list);
        lRUCache.put(2, 2);
//        System.out.println(lRUCache.list);
//        System.out.println(lRUCache.get(1));
        lRUCache.put(3, 3);
//        System.out.println(lRUCache.get(2));
        lRUCache.put(4, 4);
        System.out.println(lRUCache.get(1));
        System.out.println(lRUCache.get(3));
        System.out.println(lRUCache.get(4));
        System.out.println(lRUCache);
    }
    private Map<K, Node<K, V>> map = new HashMap<>();
    private int capacity;

    private Node<K, V> firstNode;
    private Node<K, V> lastNode;

    public HashMapLinkedListLRU(int capacity) {
        this.capacity = capacity;
        lastNode = new Node<>(null,null,null,null);
        firstNode = new Node<>(null,null,null,null);
        firstNode.next = lastNode;
        lastNode.pre = firstNode;
    }

    public V get(int key) {
        Node<K, V> node = map.get(key);
        if(node != null){
            node.next.pre = node.pre;
            node.pre.next = node.next;

            node.pre = firstNode;
            node.next = firstNode.next;
            firstNode.next.pre = node;
            firstNode.next = node;
            return node.value;
        }
        return null;
    }

    public void put(K key, V value) {
        Node<K, V> node = map.get(key);
        if(node == null){
            Node<K, V> newNode = new Node<>(firstNode, key, value, firstNode.next);
            firstNode.next.pre = newNode;
            firstNode.next = newNode;
            if(map.size()>=capacity){
                map.remove(lastNode.pre.key);
                lastNode.pre=lastNode.pre.pre;
                lastNode.pre.pre.next = lastNode;
            }
            map.put(key, newNode);
        }else {
            node.value = value;
            node.next.pre = node.pre;
            node.pre.next = node.next;
            node.pre = firstNode;
            node.next = firstNode.next;
            firstNode.next = node;
        }
    }

    @Override
    public String toString() {
        return "HashMapLinkedListLRU{" +
                "firstNode=" + firstNode +
                '}';
    }

    static class Node<K, V> {
        private K key;
        private V value;

        private Node<K, V> pre;
        private Node<K, V> next;

        public Node(Node<K, V> pre, K key, V value, Node<K, V> next) {
            this.pre = pre;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    '}';
        }
    }
}
