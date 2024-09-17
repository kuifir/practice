package com.kuifir.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树的孩子兄弟表示法
 * 左孩子，右兄弟
 */
public class ChildBrotherExpressTree<T> {
    private Node<T> root;


    public Forest<T> toForest() {
        if (root == null) {
            return new Forest<>(null);
        }
        List<TreeNode<T>> roots = new ArrayList<>();
        toTreeNodes(root, roots);
        return new Forest<>(roots);
    }

    public MultiwayTree<T> toMultiwayTree() {
        if (root == null) {
            return new MultiwayTree<>(null);
        }
        List<TreeNode<T>> roots = new ArrayList<>();
        toTreeNodes(root, roots);
        return new MultiwayTree<>(roots.isEmpty() ? null : roots.getFirst());
    }

    void toTreeNodes(Node<T> node, List<TreeNode<T>> treeNodes) {
        if (node == null) {
            return;
        }
        TreeNode<T> currentNode = new TreeNode<>();
        currentNode.setData(node.data);
        treeNodes.add(currentNode);

        if (node.firstChild != null) {
            List<TreeNode<T>> childTreeNodes = new ArrayList<>();
            toTreeNodes(node.firstChild, childTreeNodes);
            currentNode.setChild(childTreeNodes);
        }
        if (node.brothers != null) {
            toTreeNodes(node.brothers, treeNodes);
        }
    }

    public void print() {
        System.out.println();
        printRootFirst(root);
        System.out.println();
        printRootLast(root);
        System.out.println();
    }

    /**
     * 树的先根序遍历/森林的先序遍历/二叉树的先序遍历
     */
    void printRootFirst(Node<T> node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        printRootFirst(node.firstChild);
        printRootFirst(node.brothers);
    }

    /**
     * 树的后根遍历/森林的中序遍历/二叉树的中序遍历
     */
    void printRootLast(Node<T> node) {
        if (node == null) {
            return;
        }
        printRootLast(node.firstChild);
        System.out.print(node.data + " ");
        printRootLast(node.brothers);
    }

    public ChildBrotherExpressTree() {
    }

    public ChildBrotherExpressTree(Node<T> root) {
        this.root = root;
    }

    static class Node<T> {
        T data;
        Node<T> firstChild;
        Node<T> brothers;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }
    }
}
