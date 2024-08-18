package com.kuifir.table.queue;

import java.util.Arrays;

public class CircularQueue<T> {
    private Object[] elements;
    private int top = 0;
    private int tail = 0;

    public CircularQueue(int capacity) {
        elements = new Object[capacity];
    }

    public boolean enqueue(T element) {
        if ((tail+1) % elements.length == top) {
            return false;
        }
        elements[tail] = element;
        tail =  (tail+1) % elements.length;
        return true;
    }

    public T dequeue() {
        if (tail == top) {
            throw new RuntimeException("空队");
        }
        T element = (T) elements[tail++];
        return element;
    }


    @Override
    public String toString() {
        return "CircularQueue{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
