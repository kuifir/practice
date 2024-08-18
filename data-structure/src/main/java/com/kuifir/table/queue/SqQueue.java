package com.kuifir.table.queue;

import java.util.Arrays;

public class SqQueue<T> {
    private Object[] elements;
    private int top = 0;
    private int tail = 0;

    public SqQueue(int capacity) {
        elements = new Object[capacity];
    }

    public boolean enqueue(T element){
        if(tail == elements.length){
            if(top == 0){
                return false;
            }
            for (int i = top; i <tail; i++) {
                elements[i-top] = elements[i];
                tail = tail - top;
                top = 0;
            }
        }
        elements[tail++] = element;
        return true;
    }
    public T dequeue(){
        if(tail == top){
            throw new RuntimeException("空队");
        }
        T element = (T) elements[tail++];
        return element;
    }

    @Override
    public String toString() {
        return "SqQueue{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
