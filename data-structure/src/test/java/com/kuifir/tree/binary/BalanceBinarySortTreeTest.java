package com.kuifir.tree.binary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BalanceBinarySortTreeTest {
    private BalanceBinarySortTree<Integer> balanceBinarySortTree;

    @BeforeEach
    void beforeEach() {
        balanceBinarySortTree = new BalanceBinarySortTree<>();
    }

    @Test
    void insert_LL_Empty() {
        IntStream.of(31, 25, 16).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }

    @Test
    void insert_LL_NotEmpty() {
        IntStream.of(31, 25, 47, 16, 28, 9).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }

    @Test
    void insert_RR_Empty() {
        IntStream.of(31, 47, 69).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }

    @Test
    void insert_RR_NotEmpty() {
        IntStream.of(31, 25, 47, 40, 69, 76).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }

    @Test
    void insert_LR_0() {
        IntStream.of(31, 25, 28).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }

    @Test
    void insert_LR_L() {
        IntStream.of(31, 25, 47, 16, 28, 26).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }

    @Test
    void insert_LR_R() {
        IntStream.of(31, 25, 47, 16, 28, 30).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }

    @Test
    void insert_RL_0() {
        IntStream.of(31, 47, 40).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }

    @Test
    void insert_RL_L() {
        IntStream.of(31, 25, 47, 40, 69, 36).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }

    @Test
    void insert_RL_R() {
        IntStream.of(31, 25, 47, 40, 69, 43).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }
}