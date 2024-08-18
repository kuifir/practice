package com.kuifir.table.queue;

public class LinkedQueue<T> {
    private Node<T> head;
    private Node<T> tail;
    public boolean enqueue(T element) {
        Node<T> newNode = new Node<>(element);
        if(head == null){
            tail = head = newNode;
        }
        tail.next = newNode;
        tail = newNode;
        return true;
    }

    public T dequeue() {
        if(head == null){
            throw new RuntimeException("空队列");
        }
        T data = head.data;
        head = head.next;
        if(head ==null){
            tail = null;
        }
        return data;
    }

    @Override
    public String toString() {
        return "LinkedQueue{" +
                "head=" + head +
                '}';
    }

    static class Node<T>{
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

}
