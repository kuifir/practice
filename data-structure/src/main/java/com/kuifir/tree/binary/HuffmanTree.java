package com.kuifir.tree.binary;

/**
 * 哈夫曼树：又称最优树，是一类带权路径长度最短的树
 * 哈夫曼树没有度为1的节点，则一课有n个叶子结点的哈夫曼树，共有2n-1个节点，可以存储在一个大小为2n-1的一维数组中。
 * 树中每个节点还要办函其双亲信息和孩子节点的信息，由此每个结点的存储建结构设计如{@link HuffmanTreeNode<T>}
 *
 * @param <T>
 */
public class HuffmanTree<T> {
    private final int capacity;
    /**
     * 为实现方便，0号下标不使用，从1号单元开始，数组的大小为2n
     */
    HuffmanTreeNode<T>[] huffmanTreeNodes;

    public HuffmanTree(int capacity) throws Exception {
        if (capacity <= 0) {
            throw new Exception("节点数量错误");
        }
        this.capacity = capacity;
        this.huffmanTreeNodes = new HuffmanTreeNode[2 * capacity];
    }

    public void init(int[] weights) throws Exception {
        if (weights.length != capacity) {
            throw new Exception("节点数量错误");
        }
        // 初始化
        for (int i = 1; i <= capacity; i++) {
            huffmanTreeNodes[i] = new HuffmanTreeNode<>(weights[i - 1]);
        }
        // 创建树：循环n-1次，通过n-1次的选择、删除、与合并 来创建哈夫曼树
        // 选择：是从当前森林中选择双亲为0且权值最小的两个数根节点s1和s2
        // 删除：是将节点s1和s2的双亲改为非0，
        // 合并：就是将s1和s2的权值之和 作为一个新节点的权值依次存入到数组的第n+1之后的单元中，同时记录这个新结点左孩子下标为s1,右孩子的下标为s2
        for (int i = capacity + 1; i < 2 * capacity; i++) {
            // 选择 删除
            Pair<Integer, Integer> pair = selectAndRemove(i);
            // 合并
            huffmanTreeNodes[i] = new HuffmanTreeNode<>(huffmanTreeNodes[pair.a].weight + huffmanTreeNodes[pair.b].weight, pair.a, pair.b);
        }
    }

    /**
     * @param index 当前下标
     * @return
     */
    Pair<Integer, Integer> selectAndRemove(int index) {
        Pair<Integer, Integer> integerPair = new Pair<>();
        for (int i = 0; i < 2; i++) {

            int min = -1;
            for (int i1 = 1; i1 < index; i1++) {
                if (huffmanTreeNodes[i1].parent != 0) {
                    continue;
                }
                if (min < 0) {
                    min = i1;
                    continue;
                }
                if (huffmanTreeNodes[i1].weight < huffmanTreeNodes[min].weight) {
                    min = i1;
                }
            }
            huffmanTreeNodes[min].parent = index;
            if (i == 0) {
                integerPair.a = min;
            } else {
                integerPair.b = min;
            }
        }
        return integerPair;
    }


    static class HuffmanTreeNode<T> {
        /**
         * 节点的权值
         */
        int weight;
        /**
         * 双亲、孩子下标
         */
        int parent, leftChild, rightChild;

        public HuffmanTreeNode(int weight) {
            this.weight = weight;
        }

        public HuffmanTreeNode(int weight, int leftChild, int rightChild) {
            this.weight = weight;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        @Override
        public String toString() {
            return "HuffmanTreeNode{" +
                    "weight=" + weight +
                    ", parent=" + parent +
                    ", leftChild=" + leftChild +
                    ", rightChild=" + rightChild +
                    '}';
        }
    }

    class Pair<A, B> {
        A a;
        B b;
    }
}
