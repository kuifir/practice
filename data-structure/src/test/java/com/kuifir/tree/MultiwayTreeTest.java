package com.kuifir.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MultiwayTreeTest {
    private MultiwayTree<Character> multiwayTree;

    @BeforeEach
    void beforeEach() {
        ParentExpressTree<Character> parentExpressTree = new ParentExpressTree<>();
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
        multiwayTree = parentExpressTree.toMultiWayTree();
    }

    @Test
    void toParentExpress() {
        multiwayTree.printLevelOrder();
        ParentExpressTree<Character> parentExpress = multiwayTree.toParentExpress();
        System.out.println(parentExpress.getTree().size());
    }

    @Test
    void toChildBrotherExpress() {
        multiwayTree.printLevelOrder();
        ChildBrotherExpressTree<Character> childBrotherExpress = multiwayTree.toChildBrotherExpress();
        childBrotherExpress.print();
        MultiwayTree<Character> multiwayTree1 = childBrotherExpress.toMultiwayTree();
    }
}