package com.kuifir.tree.binary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

class ThreadedBinaryTreeTest {

    ThreadedBinaryTree<Character> threadedBinaryTree;

    @BeforeEach
    void beforeEach() {
        threadedBinaryTree = new ThreadedBinaryTree<>();
    }

    @Test
    void inThreading() {
        LinkedList<Character> deque = Stream.of('-', '+', 'a', null, null, '*', 'b', null, null, '-', 'c', null, null, 'd', null, null, '/', 'e', null, null, 'f', null, null)
                .collect(LinkedList::new, LinkedList::addLast, LinkedList::addAll);
        threadedBinaryTree.initNode(deque);
        threadedBinaryTree.inOrderThreading();
        threadedBinaryTree.print();
    }
}