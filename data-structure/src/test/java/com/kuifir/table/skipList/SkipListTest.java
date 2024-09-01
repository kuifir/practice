package com.kuifir.table.skipList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SkipListTest {

    SkipList<Integer> skipList = new SkipList<>();

    @BeforeEach
    void beforeAll() {
        skipList.insert(1);
        skipList.insert(2);
        skipList.insert(6);
        skipList.insert(7);
        skipList.insert(8);
        skipList.insert(3);
        skipList.insert(4);
        skipList.insert(5);
    }

    @Test
    void find() {
        skipList.printAll();
        System.out.println(skipList.find(5));
    }

    @Test
    void insert() {
        skipList.printAll();
        skipList.insert(5);
        skipList.insert(5);
        skipList.insert(5);
        skipList.insert(5);
        skipList.printAll();
    }

    @Test
    void delete() {
        skipList.printAll();
        skipList.insert(5);
        skipList.insert(5);
        skipList.insert(5);
        skipList.insert(5);
        skipList.printAll();
        skipList.printAll();
        skipList.delete(5);
        skipList.printAll();
        skipList.delete(5);
        skipList.printAll();
        skipList.delete(5);
        skipList.printAll();
        skipList.delete(5);
        skipList.printAll();
        skipList.delete(1);
        skipList.printAll();
        System.out.println(skipList.find(1));
    }
}