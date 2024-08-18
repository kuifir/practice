package com.kuifir.table.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkStackTest {
    LinkStack<String> sqStack;

    @BeforeEach
    public void beforeAll() {
        sqStack = new LinkStack<>();
    }

    @Test
    void pop() {
        System.out.println(sqStack);
        assertThrows(RuntimeException.class,sqStack::pop);
        sqStack.push("aa");
        sqStack.push("bb");
        System.out.println(sqStack);
        assertEquals("bb",sqStack.pop());
        assertEquals("aa",sqStack.pop());
        assertThrows(RuntimeException.class,sqStack::pop);
    }

    @Test
    void push() {
        sqStack.push("aa");
        sqStack.push("bb");
        assertTrue(sqStack.push("cc"));
    }
}