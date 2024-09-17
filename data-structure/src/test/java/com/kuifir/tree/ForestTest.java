package com.kuifir.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ForestTest {
    Forest forest;

    @BeforeEach
    void beforeEach() {

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
                new ParentExpressTree.Node<>(10, 'G', null),
                new ParentExpressTree.Node<>(11, 'K', 10),
                new ParentExpressTree.Node<>(12, 'L', 10),
                new ParentExpressTree.Node<>(13, 'M', 10),
                new ParentExpressTree.Node<>(14, 'N', 11),
                new ParentExpressTree.Node<>(15, 'O', 11),
                new ParentExpressTree.Node<>(16, 'P', 13),
                new ParentExpressTree.Node<>(17, 'Q', 16),
                new ParentExpressTree.Node<>(18, 'R', 16),
                new ParentExpressTree.Node<>(19, 'S', 16),
                new ParentExpressTree.Node<>(20, 'T', null)
        ).collect(Collectors.toList());

        ParentExpressTree<Character> parentExpressTree = new ParentExpressTree<>();
        parentExpressTree.setTree(nodeList);
        forest = parentExpressTree.toForest();
    }

    @Test
    void toParentExpress() {
        ParentExpressTree parentExpress = forest.toParentExpress();
        System.out.println(parentExpress);
    }

    @Test
    void toChildBrotherExpress() {
        ChildBrotherExpressTree childBrotherExpress = forest.toChildBrotherExpress();
        childBrotherExpress.print();
    }
}