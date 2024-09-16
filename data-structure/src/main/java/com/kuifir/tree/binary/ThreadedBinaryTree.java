package com.kuifir.tree.binary;

import java.util.List;
import java.util.Queue;

public class ThreadedBinaryTree<T> {
    private BiThreadNode<T> root;
    private BiThreadNode<T> head;

    /**
     * 中序线索化
     */
    public void inOrderThreading() {
        BiThreadNode<T> extendNode = new BiThreadNode<>();
        head = extendNode;
        extendNode.left = root;
        extendNode.rightThreadFlag = true;
        extendNode.right = extendNode;
        if (root == null) {
            extendNode.left = extendNode;
        }
        BiThreadNode<T> pre = extendNode;
        pre = inThreading(pre, root);
        pre.rightThreadFlag = true;
        pre.right = extendNode;
        extendNode.right = pre;

    }

    BiThreadNode<T> inThreading(BiThreadNode<T> pre, BiThreadNode<T> node) {
        if (node != null) {
            pre = inThreading(pre, node.left);
            if (node.left == null) {
                node.leftThreadFlag = true;
                node.left = pre;
            }
            if (pre.right == null) {
                pre.rightThreadFlag = true;
                pre.right = node;
            }
            pre = node;
            pre = inThreading(pre, node.right);
        }
        return pre;
    }

    /**
     * 不支持重复节点
     *
     * @param preList
     * @param inOrderList
     * @throws Exception
     */
    public void initByPreAndInOrder(List<T> preList, List<T> inOrderList) throws Exception {
        root = initParentByPreAndInOrder(preList, inOrderList);
    }

    BiThreadNode<T> initParentByPreAndInOrder(List<T> preList, List<T> inOrderList) throws Exception {
        if (preList == null || preList.isEmpty()) {
            return null;
        }
        if (preList.size() == 1) {
            return new BiThreadNode<>(preList.getFirst());
        }
        // t为根节点，查找左右子树
        T parent = preList.getFirst();
        BiThreadNode<T> node = new BiThreadNode<>(parent);
        for (int i = 0; i < inOrderList.size(); i++) {
            // 中序遍历，根节点在中间
            if (inOrderList.get(i).equals(parent)) {
                List<T> leftInOrderChild = inOrderList.subList(0, i);
                List<T> rightInOrderChild = inOrderList.subList(i + 1, inOrderList.size());
                List<T> leftPreOrderChild = null;
                List<T> rightPreOrderChild = null;
                if (leftInOrderChild.isEmpty()) {
                    rightPreOrderChild = preList.subList(1, preList.size());
                } else {
                    for (int i1 = 0; i1 < preList.size(); i1++) {
                        if (preList.get(i1).equals(leftInOrderChild.getLast())) {
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

    /**
     * 全节点先序遍历构造
     *
     * @param list
     */
    public void initNode(Queue<T> list) {
        initNode(root, list);
    }

    BiThreadNode<T> initNode(BiThreadNode<T> node, Queue<T> list) {
        T poll = list.remove();
        if (poll == null) {
            node = null;
        } else {
            BiThreadNode<T> newNode = new BiThreadNode<>(poll);
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
        printInOrderByInThread(root);
    }

    void printInOrderByInThread(BiThreadNode<T> node) {
        if (node == null) {
            return;
        }
        while (node != head) {
            while (!node.leftThreadFlag) {
                node = node.left;
            }
            System.out.print(node.data + " ");
            while (node.rightThreadFlag && node.right != head) {
                node = node.right;
                System.out.print(node.data + " ");
            }
            node = node.right;
        }


    }

    static class BiThreadNode<T> {
        T data;
        boolean leftThreadFlag = false;
        BiThreadNode<T> left;
        boolean rightThreadFlag = false;
        BiThreadNode<T> right;

        public BiThreadNode() {
        }

        public BiThreadNode(T data) {
            this.data = data;
        }
    }
}
