package com.kuifir.tree.binary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

class BinaryTreeTest {
    private BinaryTree<Character> binaryTree;

    @BeforeEach
    void beforeEach() {
        binaryTree = new BinaryTree<>();
    }

    @Test
    void initNode() {
        LinkedList<Character> deque = Stream.of('A', 'B', 'C', null, null, 'D', 'E', null, 'G', null, null, 'F', null, null, null)
                .collect(LinkedList::new, LinkedList::addLast, LinkedList::addAll);
        binaryTree.initNode(deque);
        binaryTree.print();

    }
    @Test
    void print(){
        LinkedList<Character> deque = Stream.of('-', '*', 'a', null,null,'b',null, null,  'c', null, null)
                .collect(LinkedList::new, LinkedList::addLast, LinkedList::addAll);
        binaryTree.initNode(deque);
        binaryTree.print();
    }
}