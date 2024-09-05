package com.kuifir.tree.binary;

import java.util.Queue;
import java.util.Stack;

public class BinaryTree<T extends Comparable<T>> {
    private Node<T> root;

    public void initNode(Queue<T> list) {
        initNode(root, list);
    }

    Node<T> initNode(Node<T> node, Queue<T> list) {
        T poll = list.remove();
        if (poll == null) {
            node = null;
        } else {
            Node<T> newNode = new Node<>(poll);
            if (node == root && node == null) {
                root = newNode;
            }
            node = newNode;
            node.left = initNode(node.left, list);
            node.right = initNode(node.right, list);
        }
        return node;
    }

    public void print() {
        System.out.println();
        printFirst_2(root);
        System.out.println();
        printMiddle_2(root);
        System.out.println();
        printLast_2(root);
    }

    void printFirst(Node<T> node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        printFirst(node.left);
        printFirst(node.right);

    }

    void printFirst_2(Node<T> node) {
        Stack<Node<T>> stack = new Stack<>();
        while (node != null || !stack.empty()) {
            if (node != null) {
                System.out.print(node.data + " ");
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                node = node.right;
            }
        }


    }

    void printMiddle(Node<T> node) {
        if (node == null) {
            return;
        }
        printMiddle(node.left);
        System.out.print(node.data + " ");
        printMiddle(node.right);
    }

    void printMiddle_2(Node<T> node) {
        Stack<Node<T>> stack = new Stack<>();
        while (node != null || !stack.empty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                System.out.print(node.data + " ");
                node = node.right;
            }
        }
    }

    void printLast(Node<T> node) {
        if (node == null) {
            return;
        }
        printLast(node.left);
        printLast(node.right);
        System.out.print(node.data + " ");
    }

    void printLast_2(Node<T> node) {
        Stack<Node<T>> stack = new Stack<>();
        Stack<Node<T>> out = new Stack<>();
        while (node != null || !stack.empty()) {
            if (node != null) {
                out.push(node);
                stack.push(node);
                node = node.right;
            } else {
                node = stack.pop();
                node = node.left;
            }
        }
        while (!out.empty()){
            System.out.print(out.pop().data + " ");
        }
    }

    static class Node<T extends Comparable<T>> {
        T data;
        Node<T> left;
        Node<T> right;

        public Node(T data) {
            this.data = data;
        }
    }
}
