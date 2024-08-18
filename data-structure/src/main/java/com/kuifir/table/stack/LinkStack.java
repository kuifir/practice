package com.kuifir.table.stack;

public class LinkStack<T> {
    private Node<T> top;

    public T pop(){
        if(top == null){
            throw new RuntimeException("空栈");
        }
        T element = top.element;
        top = top.next;
        return element;
    }

    public boolean push (T element){
        if(top == null){
            top = new Node<>(element);
        }else {
            top = new Node<>(element,top);
        }
        return true;
    }

    static class Node<T>{
        public Node(T element) {
            this.element = element;
        }

        public Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }

        private T element;
        private Node<T> next;

        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    ", next=" + next +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LinkStack{" +
                "top=" + top +
                '}';
    }
}
