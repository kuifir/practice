package com.kuifir.tree.binary;

import com.kuifir.tree.interfaces.SortTree;

import java.util.Stack;

/**
 * 二叉排序树
 * 二叉排序树或者是一颗空树，或者是具有下列性质的二叉树:
 * 1. 若它的左子树不空，则左子树上所有结点的值均小于它的根节点的值
 * 2. 若它的右子树不空，则右子树上的所有节点均大于它的根结点的值
 * 3. 它的左子树、右子树也分别为二叉排序树
 * @param <T>
 */
public class BinarySortTree<T extends Comparable<T>> implements SortTree<T> {
    private Node<T> root;

    /**
     * 递归查询
     *
     * @param element
     * @return
     */
    public Node<T> search(T element) {
        return search(root, element);
    }

    Node<T> search(Node<T> root, T element) {
        if (root == null) {
            return null;
        }
        if (root.data.compareTo(element) == 0) {
            return root;
        } else if (root.data.compareTo(element) > 0) {
            return search(root.left, element);
        } else {
            return search(root.right, element);
        }
    }

    public void insert(T element) {
        if (root == null) {
            root = new Node<>(element);
            return;
        }
        Node<T> tmp = root;
        while (true) {
            if (tmp.data.compareTo(element) <= 0) {
                if (tmp.right == null) {
                    tmp.right = new Node<>(element);
                    break;
                } else {
                    tmp = tmp.right;
                }
            } else {
                if (tmp.left == null) {
                    tmp.left = new Node<>(element);
                    break;
                }
                tmp = tmp.left;
            }
        }
    }


    public void delete(T element) {
        Node<T> tmp = root;
        Node<T> parent = null;

        while (tmp != null) {
            if (tmp.data.compareTo(element) == 0) {
                break;
            }
            parent = tmp;
            if (tmp.data.compareTo(element) < 0) {
                tmp = tmp.right;
            } else {
                tmp = tmp.left;
            }
        }
        // 不存在节点不做处理
        if (tmp == null) {
            return;
        }

        // 查找到元素位置
        Node<T> ele = tmp;
        // 分为三种情况处理子节点：左子树右子树都不为空；只有左子树，只有右子树；
        if (tmp.left != null && tmp.right != null) {
            // 都不为空时，两种处理方式：
            // 1. 另删除节点左子树为父节点左子树，删除节点的 右子树为 左子树 的 最右子树 的右子树（可能增加深度）
            // 2. 另删除节点的直接前驱（后继）替代删除节点，然后再从二叉排序树中删除它的直接前驱（后继）（不会增加深度）
            Node<T> leftRightest = tmp.left;
            while (leftRightest.right != null) {
                tmp = leftRightest;
                leftRightest = leftRightest.right;
            }
            ele.data = leftRightest.data;
            if (ele != tmp) {
                tmp.right = leftRightest.left;
            } else {
                tmp.left = leftRightest.left;
            }
            return;

        } else if (tmp.left != null) {
            tmp = tmp.left;
        } else if (tmp.right != null) {
            tmp = tmp.right;
        }

        // 后两种情况，看删除节点在哪个节点
        if (parent == null) {
            root = tmp;
        } else {
            if (parent.left == ele) {
                parent.left = tmp;
            } else {
                parent.right = tmp;
            }
        }
    }

    public void printAll() {
        printAll(root);
    }

    /**
     * 递归中序遍历
     *
     * @param node
     */
    void printAll(Node<T> node) {
        if (node != null) {
            if (node.left != null) {
                printAll(node.left);
            }
            System.out.print(node);
            if (node.right != null) {
                printAll(node.right);
            }
        }
    }

    /**
     * 非递归中序遍历
     *
     * @param
     */
    public void print() {
        Node<T> tmp = root;
        Stack<Node<T>> stack = new Stack<>();
        while (tmp != null || !stack.empty()) {
            if (tmp != null) {
                stack.push(tmp);
                tmp = tmp.left;
            } else {
                Node<T> pop = stack.pop();
                System.out.print(pop);
                tmp = pop.right;
            }

        }
    }

    static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        public Node(E data) {
            this.data = data;
        }

        public Node(E data, Node<E> left, Node<E> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return data + " ";
        }
    }
}
