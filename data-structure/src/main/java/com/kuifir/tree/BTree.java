package com.kuifir.tree;

import com.kuifir.tree.interfaces.SortTree;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * B-树
 * 一颗m阶的B-树，或为空树，或为满足下列特性的m叉树
 * 1. 树中每个节点至多有m科子树
 * 2. 若根节点不是叶子节点，则至少有两颗子树
 * 3. 除根之外的所有非终端节点至少有(m+1)/2棵子树
 * 4. 所有的叶子节点都出现在同一层次上，并且不带信息，通常成为失败节点（失败节点并不存在，指向这些节点的指针为空，引入失败节点是为了便于分析B-树的查找性能）
 * 5. 所有的非终端节点最多有m-1个关键字
 */
public class BTree<T extends Comparable<T>> implements SortTree<T> {
    /**
     * 树的阶数(最大子树数量)
     */
    private final int m;
    private int size;
    private BTNode<T> root;

    public BTree(int m) {
        this.m = m;
    }

    public void insert(T e) {
        Result<T> search = search(e);
        if (search.success) {
            return;
        }
        BTNode<T> node = search.node;
        insert(node, e, null, null);
    }

    private void insert(BTNode<T> node, T e, BTNode<T> leftChild, BTNode<T> rightChild) {
        if (node == null) {
            // 根节点插入数据
            T[] data = (T[]) new Comparable[m];
            BTNode<T>[] child = new BTNode[m + 1];
            data[0] = e;
            child[0] = leftChild;
            child[1] = rightChild;
            root = new BTNode<>(1, null, data, child);
            size++;
            if (leftChild != null) {
                leftChild.parent = root;
            }
            if (rightChild != null) {
                rightChild.parent = root;
            }
            return;
        }
        int keyNum = node.keyNum;
        // 节点不满的时候，直接添加节点
        // 按顺序插入到位置
        int i = 0;
        while (i < keyNum && node.data[i].compareTo(e) < 0) {
            i++;
        }
        // 找到插入位置后先插入
        for (int j = m - 1; j > i; j--) {
            node.data[j] = node.data[j - 1];
            node.child[j + 1] = node.child[j];
        }
        node.data[i] = e;
        node.child[i + 1] = rightChild;
        // 判断是否需要分裂
        if (keyNum < m - 1) {
            // 不需要分裂
            node.keyNum++;
            size++;
        } else if (keyNum == m - 1) {
            int middle = (m + 1) / 2;
            BTNode<T> parent = node.parent;
            // 以中间节点分裂，中间节点提升到父节点,左侧为左节点，右侧为右节点
            T middleData = node.data[middle - 1];

            // 构造右节点
            T[] rightData = (T[]) new Comparable[m];
            BTNode<T>[] rc = new BTNode[m + 1];

            // 分裂，以中间关键字为界，左右数据填充处理
            for (int k = 0, j = middle; j < m; j++, k++) {
                rightData[k] = node.data[j];
                node.data[j] = null;
                rc[k] = node.child[j];
                node.child[j] = null;
            }
            rc[m - middle] = node.child[m];
            BTNode<T> rightNode = new BTNode<>(m - middle, parent, rightData, rc);

            // 左节点修改
            node.keyNum = middle - 1;
            node.child[m] = null;
            node.data[middle - 1] = null;
            // 添加中间节点到父节点
            insert(parent, middleData, node, rightNode);
        }
    }

    public void print() {
        print(root);
    }

    void print(BTNode<T> t) {
        if (t == null) {
            return;
        }
        for (int i = 0; i < t.keyNum; i++) {
            print(t.child[i]);
            System.out.print((t.data[i]));
            System.out.println();
            if (i == t.keyNum - 1) {
                print(t.child[i + 1]);
            }
        }
    }

    Result<T> search(T e) {
        return search(root, e, new Result<>(null, -1, false));
    }

    public boolean isBTree() {
        if (root == null) {
            return true;
        }
        if (Arrays.stream(root.child).allMatch(Objects::isNull)) {
            return true;
        }
        long count = Arrays.stream(root.child).filter(Objects::nonNull).count();
        if (count < 2 || count > m) {
            return false;
        }
        return Arrays.stream(root.child).filter(Objects::nonNull).allMatch(this::isBTreeNode);
    }

    boolean isBTreeNode(BTNode<T> node) {
        long count = Arrays.stream(node.child).filter(Objects::nonNull).count();
        long dataCount = Arrays.stream(node.data).filter(Objects::nonNull).count();
        if (count != 0) {
            if (count < ((m + 1) / 2) || count > m) {
                return false;
            }
            if (count != dataCount + 1) {
                return false;
            }
        }
        int high = getHigh(node);
        return Arrays.stream(node.child).filter(Objects::nonNull).map(this::getHigh).noneMatch(h -> h != high - 1);
    }

    int getHigh(BTNode<T> node) {
        if (node == null) {
            return 1;
        }
        Optional<Integer> max = Arrays.stream(node.child).map(this::getHigh).max(Integer::compareTo);
        return max.get() + 1;
    }

    /**
     * @param node
     * @param e
     * @param result 成功时返回相应接待你，失败时返回最接近查询的叶子节点
     * @return
     */
    Result<T> search(BTNode<T> node, T e, Result<T> result) {
        if (node == null) {
            return result;
        }
        int i = 0;
        while (i < node.keyNum - 1 && node.data[i].compareTo(e) < 0) {
            i++;
        }
        int compareTo = node.data[i].compareTo(e);

        result.node = node;
        if (compareTo == 0) {
            result.success = true;
            result.index = i;
            return result;
        } else if (compareTo > 0) {
            return search(node.child[i], e, result);
        } else {
            return search(node.child[i + 1], e, result);
        }
    }

    class Result<T extends Comparable<T>> {
        private BTNode<T> node;
        private int index;
        private boolean success;

        public Result(BTNode<T> node, int index, boolean success) {
            this.node = node;
            this.index = index;
            this.success = success;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "node=" + node +
                    ", index=" + index +
                    ", success=" + success +
                    '}';
        }
    }

    class BTNode<T extends Comparable<T>> {
        /**
         * 关键子数量
         */
        int keyNum;
        BTNode<T> parent;
        T[] data;
        /**
         * child[i] 为data[i]的左子树，
         * child[i+1]为data[i]的右子树
         */
        BTNode<T>[] child;

        public BTNode(int keyNum, BTNode<T> parent, T[] data, BTNode<T>[] child) {
            this.keyNum = keyNum;
            this.parent = parent;
            this.data = data;
            Arrays.stream(child).filter(Objects::nonNull).forEach(node -> node.parent = this);
            this.child = child;
        }

        @Override
        public String toString() {
            return "{" +
                    "keyNum=" + keyNum +
                    ", data=" + Arrays.toString(data) +
                    '}';
        }
    }
}
