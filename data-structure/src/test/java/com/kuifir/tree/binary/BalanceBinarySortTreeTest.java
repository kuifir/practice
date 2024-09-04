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
    @Test
    void delete_simple(){
        IntStream.of(60).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        balanceBinarySortTree.delete(65);
        System.out.println();
        balanceBinarySortTree.print();
        balanceBinarySortTree.delete(60);
        System.out.println();
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }
    @Test
    void delete_one_child(){
        IntStream.of(60,61).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        System.out.println();
        balanceBinarySortTree.delete(61);
        balanceBinarySortTree.print();
        System.out.println();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }
    @Test
    void delete_two_child(){
        IntStream.of(60,61,62).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        System.out.println();
        balanceBinarySortTree.delete(60);
        balanceBinarySortTree.print();
        System.out.println();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }
    @Test
    void delete_RR(){
        IntStream.of(10,9,12,13).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        System.out.println();
        balanceBinarySortTree.delete(9);
        balanceBinarySortTree.print();
        System.out.println();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }
    @Test
    void delete_RR_2(){
        IntStream.of(10,9,12,11,13).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        System.out.println();
        balanceBinarySortTree.delete(9);
        balanceBinarySortTree.print();
        System.out.println();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }
    @Test
    void delete_RL(){
        IntStream.of(10,9,12,11).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        System.out.println();
        balanceBinarySortTree.delete(60);
        balanceBinarySortTree.print();
        System.out.println();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }
    @Test
    void delete_LL(){
        IntStream.of(10,8,12,6,9).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        System.out.println();
        balanceBinarySortTree.delete(9);
        balanceBinarySortTree.print();
        System.out.println();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }
    @Test
    void delete_LL_2(){
        IntStream.of(10,8,12,6).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        System.out.println();
        balanceBinarySortTree.delete(12);
        balanceBinarySortTree.print();
        System.out.println();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }
    @Test
    void delete_LR(){
        IntStream.of(10,8,12,9).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        System.out.println();
        balanceBinarySortTree.delete(12);
        balanceBinarySortTree.print();
        System.out.println();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }

    @Test
    void delete(){
        IntStream.of(60,31,65,15,42,62,75,12,25,37,50,63,69,85,2,32,38,48,56,82,34).forEach(balanceBinarySortTree::insert);
        balanceBinarySortTree.print();
        balanceBinarySortTree.delete(65);
        System.out.println();
        balanceBinarySortTree.print();
        assertEquals(true, balanceBinarySortTree.isBalanceBinarySortTree());
    }
}