package com.kuifir.tree.binary;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<T extends Comparable<T>> {
    private Node<T> root;


    /**
     * @param preList     二叉树先序遍历结果，内容不包含null节点
     * @param inOrderList 二叉树中序遍历结果，内容不包含null节点
     * @throws Exception
     */
    public void initByPreAndInOrder(List<T> preList, List<T> inOrderList) throws Exception {
        root = initParentByPreAndInOrder(preList, inOrderList);
    }

    Node<T> initParentByPreAndInOrder(List<T> preList, List<T> inOrderList) throws Exception {
        if (preList == null || preList.isEmpty()) {
            return null;
        }
        if (preList.size() == 1) {
            return new Node<>(preList.getFirst());
        }
        // t为根节点，查找左右子树
        T parent = preList.getFirst();
        Node<T> node = new Node<>(parent);
        for (int i = 0; i < inOrderList.size(); i++) {
            // 中序遍历，根节点在中间
            if (inOrderList.get(i).compareTo(parent) == 0) {
                List<T> leftInOrderChild = inOrderList.subList(0, i);
                List<T> rightInOrderChild = inOrderList.subList(i + 1, inOrderList.size());
                List<T> leftPreOrderChild = null;
                List<T> rightPreOrderChild = null;
                if(leftInOrderChild.isEmpty()){
                   rightPreOrderChild = preList.subList(1, preList.size());
                }else {
                    for (int i1 = 0; i1 < preList.size(); i1++) {
                        if (preList.get(i1).compareTo(leftInOrderChild.getLast()) == 0) {
                            leftPreOrderChild = preList.subList(1, i1 + 1);
                            rightPreOrderChild = preList.subList(i1 + 1, preList.size());
                        }
                    }
                }

                node.left = initParentByPreAndInOrder(leftPreOrderChild, leftInOrderChild);
                node.right = initParentByPreAndInOrder(rightPreOrderChild, rightInOrderChild);
                break;
            }
        }
        return node;
    }

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
        printPreOrder2(root);
        System.out.println();
        printInOrder2(root);
        System.out.println();
        printPostOrder2(root);
    }

    void printPreOrder(Node<T> node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        printPreOrder(node.left);
        printPreOrder(node.right);

    }

    void printPreOrder2(Node<T> node) {
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

    void printInOrder(Node<T> node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.print(node.data + " ");
        printInOrder(node.right);
    }

    void printInOrder2(Node<T> node) {
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

    void printPostOrder(Node<T> node) {
        if (node == null) {
            return;
        }
        printPostOrder(node.left);
        printPostOrder(node.right);
        System.out.print(node.data + " ");
    }

    void printPostOrder2(Node<T> node) {
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
        while (!out.empty()) {
            System.out.print(out.pop().data + " ");
        }
    }

    static class Node<T extends Comparable<T>> {
        T data;
        Node<T> left;
        Node<T> right;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }
    }
}
