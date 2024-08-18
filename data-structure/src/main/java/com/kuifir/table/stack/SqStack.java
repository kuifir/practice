package com.kuifir.table.stack;

import java.util.Arrays;

/**
 * 顺序栈
 * @param <T>
 */
public class SqStack<T> {
    private Object[] elements;
    private int top = 0;
    public SqStack(int capacity) {
        elements = new Object[capacity];
    }

    public T pop(){
        if(top == 0){
            throw new RuntimeException("空栈");
        }
        return (T) elements[--top];
    }


    public boolean push(T element){
        if(top == elements.length){
            return false;
        }
        elements[top++] = element;
        return true;
    }

    @Override
    public String toString() {
        return "SqStack{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
