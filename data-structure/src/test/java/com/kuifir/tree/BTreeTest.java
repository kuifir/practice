package com.kuifir.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

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
    void delete() {
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
        System.out.println();
        bTree.delete(45);
        bTree.print();
        System.out.println();
        bTree.delete(90);
        bTree.print();
        System.out.println();
        bTree.delete(100);
        bTree.print();

        System.out.println();
        bTree.delete(3);
        bTree.print();
        System.out.println();
        bTree.delete(24);
        bTree.print();
        System.out.println();
        bTree.delete(37);
        bTree.print();
        System.out.println();
        bTree.delete(53);
        bTree.print();
        assertEquals(true, bTree.isBTree());
    }

    @Test
    void m5() {
        bTree = new BTree<>(5);
        IntStream.of(39, 22, 97, 41, 53)
                .forEach(bTree::insert);
        bTree.print();
        System.out.println();
        IntStream.of(13, 21, 40)
                .forEach(bTree::insert);
        bTree.print();
        System.out.println();
        IntStream.of(30, 27, 33, 36, 35, 34, 24, 29)
                .forEach(bTree::insert);
        bTree.print();
        System.out.println();
        IntStream.of(26)
                .forEach(bTree::insert);
        bTree.print();
        System.out.println();
        IntStream.of(17, 28, 29, 31, 32,23)
                .forEach(bTree::insert);
        bTree.print();
        System.out.println();

        bTree.delete(21);
        bTree.print();
        System.out.println();
        bTree.delete(27);
        bTree.print();
        System.out.println();
        bTree.delete(32);
        bTree.print();
        System.out.println();
        bTree.delete(40);
        bTree.print();
        System.out.println();
    }
}