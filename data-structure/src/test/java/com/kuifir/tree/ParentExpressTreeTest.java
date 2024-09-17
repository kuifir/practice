package com.kuifir.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ParentExpressTreeTest {
    ParentExpressTree<Character> parentExpressTree;

    @BeforeEach
    void beforeEach() {
        parentExpressTree = new ParentExpressTree<>();
        List<ParentExpressTree.Node<Character>> nodeList = Stream.of(new ParentExpressTree.Node<>(0, 'R', null),
                new ParentExpressTree.Node<>(1, 'A', 0),
                new ParentExpressTree.Node<>(2, 'B', 0),
                new ParentExpressTree.Node<>(3, 'C', 0),
                new ParentExpressTree.Node<>(4, 'D', 1),
                new ParentExpressTree.Node<>(5, 'E', 1),
                new ParentExpressTree.Node<>(6, 'F', 3),
                new ParentExpressTree.Node<>(7, 'G', 6),
                new ParentExpressTree.Node<>(8, 'H', 6),
                new ParentExpressTree.Node<>(9, 'I', 6)
        ).collect(Collectors.toList());
        parentExpressTree.setTree(nodeList);
    }

    @Test
    void toMultiWayTree() {
        MultiwayTree<Character> multiWayTree = parentExpressTree.toMultiWayTree();
        multiWayTree.printLevelOrder();
        System.out.println();

    }
    @Test
    void toForest() {
        List<ParentExpressTree.Node<Character>> nodeList = Stream.of(
                new ParentExpressTree.Node<>(0, 'R', null),
                new ParentExpressTree.Node<>(1, 'A', 0),
                new ParentExpressTree.Node<>(2, 'B', 0),
                new ParentExpressTree.Node<>(3, 'C', 0),
                new ParentExpressTree.Node<>(4, 'D', 1),
                new ParentExpressTree.Node<>(5, 'E', 1),
                new ParentExpressTree.Node<>(6, 'F', 3),
                new ParentExpressTree.Node<>(7, 'G', 6),
                new ParentExpressTree.Node<>(8, 'H', 6),
                new ParentExpressTree.Node<>(9, 'I', 6),
                new ParentExpressTree.Node<>(10, 'R', null),
                new ParentExpressTree.Node<>(11, 'A', 10),
                new ParentExpressTree.Node<>(12, 'B', 10),
                new ParentExpressTree.Node<>(13, 'C', 10),
                new ParentExpressTree.Node<>(14, 'D', 11),
                new ParentExpressTree.Node<>(15, 'E', 11),
                new ParentExpressTree.Node<>(16, 'F', 13),
                new ParentExpressTree.Node<>(17, 'G', 16),
                new ParentExpressTree.Node<>(18, 'H', 16),
                new ParentExpressTree.Node<>(19, 'I', 16)
        ).collect(Collectors.toList());
        parentExpressTree.setTree(nodeList);
        Forest<Character> forest = parentExpressTree.toForest();
        forest.print();
    }
}