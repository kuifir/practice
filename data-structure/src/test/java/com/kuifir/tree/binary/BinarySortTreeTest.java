package com.kuifir.tree.binary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class BinarySortTreeTest {
    BinarySortTree<Integer> binarySortTree = new BinarySortTree<>();

    @BeforeEach
    void beforeEach() {
        Stream.of(45, 12, 53, 3, 37, 100, 24, 61, 90, 78).forEach(binarySortTree::insert);
    }

    @Test
    void search() {
        BinarySortTree.Node<Integer> search = binarySortTree.search(100);
        System.out.println(search);
    }

    @Test
    void delete() {
        binarySortTree.printAll();
        binarySortTree.delete(2);
        System.out.println();
        binarySortTree.printAll();
    }

    @Test
    void printAll() {
        binarySortTree.printAll();
        System.out.println();
        binarySortTree.print();
    }

}