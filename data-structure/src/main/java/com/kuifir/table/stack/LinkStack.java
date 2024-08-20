package com.kuifir.table.stack;

public class LinkStack<T> {
    private Node<T> top;

    public boolean isEmpty() {
        return top == null;
    }

    public T pop() {
        if (top == null) {
            throw new RuntimeException("空栈");
        }
        T element = top.element;
        top = top.next;
        return element;
    }

    public boolean push(T element) {
        if (top == null) {
            top = new Node<>(element);
        } else {
            top = new Node<>(element, top);
        }
        return true;
    }
    public T getTop(){
        if(top == null){
            return null;
        }
        return top.element;
    }

    static class Node<T> {
        public Node(T element) {
            this.element = element;
        }

        public Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }

        private T element;
        private Node<T> next;

    }

    @Override
    public String toString() {
        if (top == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        Node<T> tmp = top;
        builder.append(tmp.element);
        while (tmp.next!=null){
            builder.append(","+tmp.next.element);
            tmp = tmp.next;
        }
        return builder.toString();
    }
}
