package com.kuifir.tree.binary;

import java.util.Stack;

/**
 * 平衡二叉树（不支持重复元素）
 * <p>
 * 平衡二叉树或者是空树，或者是具有如下性质的二叉排序树{@link BinarySortTree}：
 * 1. 左子树和右子树的深度之差的绝对值不超过1
 * 2. 左子树和右子树也是平衡二叉树
 */
public class BalanceBinarySortTree<T extends Comparable<T>> {
    private Node<T> root;

    Node<T> search(T e) {
        return search(root, e);
    }

    Node<T> search(Node<T> node, T e) {
        Node<T> tmp = node;
        while (tmp != null) {
            if (tmp.data.compareTo(e) > 0) {
                tmp = tmp.left;
            } else if (tmp.data.compareTo(e) < 0) {
                tmp = tmp.right;
            } else {
                return tmp;
            }
        }
        return null;
    }

    void print() {
        Node<T> tmp = root;
        Stack<Node<T>> stack = new Stack<>();
        while (tmp != null || !stack.empty()) {
            if (tmp != null) {
                stack.push(tmp);
                tmp = tmp.left;
            } else {
                tmp = stack.pop();
                System.out.print(tmp);
                tmp = tmp.right;
            }
        }
    }

    public void insert(T e) {
        Node<T> search = search(e);
        if (search != null) {
            return;
        }
        insert(root, e);
    }

    int getHigh(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return Math.max(getHigh(node.left), getHigh(node.right)) + 1;
    }

    Node<T> getTallerChild(Node<T> node) {
        return getHigh(node.left) >= getHigh(node.right) ? node.left : node.right;
    }

    public Boolean isBalanceBinarySortTree() {
        return isBalanceBinarySortTree(root);
    }

    Boolean isBalanceBinarySortTree(Node<T> node) {
        if (node == null) {
            return true;
        }
        Boolean isBalance = Math.abs(getHigh(node.right) - getHigh(node.left)) <= 1;
        return isBalance && isBalanceBinarySortTree(node.left) && isBalanceBinarySortTree(node.right);
    }

    int getBalanceFactor(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return getHigh(node.left) - getHigh(node.right);
    }

    /**
     * 顺时针旋转 右旋 LL
     *
     * @param node
     */
    void rightRotate(Node<T> node) {
        //node.left = left.right;
        Node<T> left = node.left;
        node.left = left.right;
        if (left.right != null) {
            left.right.parent = node;
        }
        // node 与 left 顺时针旋转
        if (node.parent != null) {
            if (node == node.parent.left) {
                node.parent.left = left;
            } else {
                node.parent.right = left;
            }
        } else {
            root = left;
        }
        left.parent = node.parent;
        left.right = node;
        node.parent = left;
        // 之前根节点为2 ，左节点为1 此时的平衡因子为0
        node.balanceFactor = getBalanceFactor(node);
        left.balanceFactor = getBalanceFactor(left);
    }

    /**
     * 逆时针旋转 左旋 RR
     *
     * @param node
     */
    void leftRotate(Node<T> node) {
        Node<T> right = node.right;
        // node.right = right.left;
        node.right = right.left;
        if (right.left != null) {
            right.left.parent = node;
        }
        // node 与 right逆时针旋转
        if (node.parent != null) {
            if (node == node.parent.left) {
                node.parent.left = right;
            } else {
                node.parent.right = right;
            }
        } else {
            root = right;
        }
        right.parent = node.parent;
        right.left = node;
        node.parent = right;
        // 之前根节点为-2 ，右节点为-1 此时的平衡因子为0
        node.balanceFactor = getBalanceFactor(node);
        right.balanceFactor = getBalanceFactor(right);
    }

    void insert(Node<T> node, T e) {
        if (root == null) {
            root = new Node<>(null, e, 0);
            return;
        }
        Node<T> tmp = node;
        int compareTo = tmp.data.compareTo(e);
        if (compareTo == 0) {
            return;
        } else if (compareTo > 0) {
            // 插入之前的长度
            int high = getHigh(node.left);
            if (node.left == null) {
                node.left = new Node<>(node, e, 0);
            } else {
                insert(node.left, e);
            }
            // 插入之后的长度
            int afterInsertHigh = getHigh(node.left);
            if (afterInsertHigh > high) {
                // 第一个异常的需要进行旋转
                if (tmp.balanceFactor < 1) {
                    node.balanceFactor++;
                } else {
                    // 需要进行旋转
                    if (tmp.left.balanceFactor == -1) {
                        // LR
                        // 先逆时针,以left为根节点进行
                        leftRotate(tmp.left);
                        // 在顺时针
                        rightRotate(tmp);
                    } else if (tmp.left.balanceFactor == 1) {
                        // LL
                        rightRotate(tmp);
                    }
                }
            }
        } else {
            // 插入之前的长度
            int high = getHigh(node.right);
            if (node.right == null) {
                node.right = new Node<>(node, e, 0);
            } else {
                insert(node.right, e);
            }
            // 插入之后的长度
            int afterInsertHigh = getHigh(node.right);
            if (afterInsertHigh > high) {
                if (tmp.balanceFactor > -1) {
                    tmp.balanceFactor--;
                } else {
                    if (tmp.right.balanceFactor == -1) {
                        leftRotate(tmp);
                    } else if (tmp.right.balanceFactor == 1) {
                        rightRotate(tmp.right);
                        leftRotate(tmp);
                    }
                }
            }

        }
    }

    static class Node<T> {
        private T data;
        /**
         * 平衡因子 绝对值<1
         */
        private int balanceFactor;
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;


        public Node(Node<T> parent, T data, int balance) {
            this.parent = parent;
            this.data = data;
            this.balanceFactor = balance;
        }

        @Override
        public String toString() {
            return data + " ";
        }
    }
}
