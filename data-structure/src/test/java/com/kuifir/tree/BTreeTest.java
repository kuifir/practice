package com.kuifir.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BTreeTest {
    private BTree<Integer> bTree;

    @BeforeEach
    void beforeEach() {
        bTree = new BTree<>(3);
    }

    @Test
    void insert() {
        IntStream.of(45, 24
                        , 53
                        , 90
                        , 3
                        , 12
                        , 37
                        , 50
                        , 61
                        , 70
                        , 100
                )
                .forEach(bTree::insert);
        bTree.print();
        assertEquals(true, bTree.isBTree());
    }

    @Test
    void search() {
    }
}