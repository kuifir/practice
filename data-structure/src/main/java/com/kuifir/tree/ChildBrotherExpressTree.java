package com.kuifir.tree;

/**
 * 树的孩子兄弟表示法
 * 左孩子，右兄弟
 */
public class ChildBrotherExpressTree<T> {
    private Node<T> root;

    public void print(){
        System.out.println();
        printRootFirst(root);
        System.out.println();
        printRootLast(root);
        System.out.println();
    }

    /**
     * 树的先根序遍历/森林的先序遍历/二叉树的先序遍历
     */
    void printRootFirst(Node<T> node){
        if(node == null){
            return;
        }
        System.out.print(node.data + " ");
        printRootFirst(node.firstChild);
        printRootFirst(node.brothers);
    }

    /**
     * 树的后根遍历/森林的中序遍历/二叉树的中序遍历
     */
    void printRootLast(Node<T> node){
        if(node == null){
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
