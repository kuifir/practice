package com.kuifir.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MultiwayTree<T> {
    private TreeNode<T> root;

    public MultiwayTree(TreeNode<T> root) {
        this.root = root;
    }

    public ParentExpressTree<T> toParentExpress() {
        ParentExpressTree<T> tParentExpressTree = new ParentExpressTree<>();
        if (root == null) {
            return tParentExpressTree;
        }
        List<ParentExpressTree.Node<T>> tree = new ArrayList<>();
        toParentExpress(tree, root, null);
        tParentExpressTree.setTree(tree);
        return tParentExpressTree;
    }

    void toParentExpress(List<ParentExpressTree.Node<T>> tree, TreeNode<T> currentNode, ParentExpressTree.Node<T> parentNode) {
        if (currentNode != null) {
            ParentExpressTree.Node<T> node = new ParentExpressTree.Node<>(tree.size(), currentNode.getData(), parentNode == null ? null : parentNode.getId());
            tree.add(node);
            List<TreeNode<T>> child = currentNode.getChild();
            if (child != null && !child.isEmpty()) {
                for (TreeNode<T> tTreeNode : child) {
                    toParentExpress(tree, tTreeNode, node);
                }
            }
        }
    }

    public ChildBrotherExpressTree<T> toChildBrotherExpress() {
        if (root == null) {
            return new ChildBrotherExpressTree<>();
        }
        ChildBrotherExpressTree.Node<T> childBrotherExpress = toChildBrotherExpress(root, new ArrayList<>());
        return new ChildBrotherExpressTree<>(childBrotherExpress);
    }

    ChildBrotherExpressTree.Node<T> toChildBrotherExpress(TreeNode<T> node, List<TreeNode<T>> brothers) {
        if (node == null) {
            return null;
        }
        ChildBrotherExpressTree.Node<T> currentNode = new ChildBrotherExpressTree.Node<>(node.getData());
        List<TreeNode<T>> child = node.getChild();
        if (child == null || child.isEmpty()) {
            currentNode.firstChild = null;
        } else {
            TreeNode<T> left = child.removeFirst();
            currentNode.firstChild = toChildBrotherExpress(left, child);
        }
        if (brothers == null || brothers.isEmpty()) {
            currentNode.brothers = null;
        } else {
            currentNode.brothers = toChildBrotherExpress(brothers.removeFirst(), brothers);
        }
        return currentNode;
    }


    public void printLevelOrder() {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            for (int i = 0; i < queue.size(); i++) {
                TreeNode<T> tmp = queue.removeFirst();
                System.out.print(tmp.getData() + " ");
                List<TreeNode<T>> child = tmp.getChild();
                if (child != null && !child.isEmpty()) {
                    queue.addAll(child);
                }
            }
        }
    }
}
