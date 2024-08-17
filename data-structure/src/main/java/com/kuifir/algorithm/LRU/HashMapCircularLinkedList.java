package com.kuifir.algorithm.LRU;

import java.util.HashMap;
import java.util.Map;

public class HashMapCircularLinkedList<K,V> {

    public static void main(String[] args) {
        HashMapCircularLinkedList<Integer, Integer> lRUCache = new HashMapCircularLinkedList<>(2);
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
    private Map<K,Node<K,V>> cache= new HashMap<>();
    private Node<K,V> picket = new Node<>(null,null);

    public HashMapCircularLinkedList(int capacity) {
        this.capacity = capacity;
        picket.pre = picket;
        picket.next = picket;
    }

    @Override
    public String toString() {
        if (cache.isEmpty()){
            return "HashMapCircularLinkedList{}";
        }
        return "HashMapCircularLinkedList{" +
                "picket=" + picket +
                '}';
    }


    public V get(int key) {
        Node<K, V> node = cache.get(key);
        if(node == null){
            return null;
        }else {
            deleteNode(node);
            moveNodeToHead(node);
            return node.value;
        }
    }

    public void put(K key, V value) {
        Node<K, V> node = cache.get(key);
        if(node == null){
            node = new Node<>(key,value);
            if(cache.size() >= capacity){
                deleteTail();
            }
            insertHead(node);
        }else {
            node.value = value;
            deleteNode(node);
            insertHead(node);
        }
    }

    public void deleteNode(Node<K,V> node){
        node.pre.next = node.next;
        node.next.pre = node.pre;
        cache.remove(node.key);
    }
    public void deleteTail(){
        Node<K, V> tailNode = picket.pre;
        deleteNode(tailNode);
    }
    public void insertHead(Node<K,V> node){
        picket.next.pre = node;

        node.next = picket.next;
        node.pre = picket;

        picket.next = node;
        cache.put(node.key,node);
    }
    public void moveNodeToHead(Node<K,V> node){
        deleteNode(node);
        insertHead(node);
    }

    static class Node<K,V>{
        private Node<K,V> pre;
        private K key;
        private V value;
        private Node<K,V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            Node<K, V> tail = pre.next;
            pre.next = null;
             String result = "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    '}';
            pre.next = tail;
            return result;
        }
    }
}
