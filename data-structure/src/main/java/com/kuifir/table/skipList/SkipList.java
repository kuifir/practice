package com.kuifir.table.skipList;

/**
 * 跳表的一种实现方法。
 * 数据不重复
 *
 * @param <T>
 */
public class SkipList<T extends Comparable<T>> {
    private static final int MAX_LEVEL = 16;
    private static final double SKIPLIST_P = 0.5;

    private int levelCount = 1;

    private Node<T> head = new Node<>();

    public Node<T> find(T element) {
        Node<T> p = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.nextNodes[i] != null && p.nextNodes[i].data.compareTo(element) < 0) {
                p = p.nextNodes[i];
            }
        }
        if (p.nextNodes[0] != null && p.nextNodes[0].data == element) {
            return p.nextNodes[0];
        } else {
            return null;
        }
    }

    public void insert(T element) {
        int level = randomLevel();
        Node<T> newNode = new Node<>();
        newNode.data = element;
        newNode.maxLevel = level;
        Node<T> p = head;
        if (levelCount < level) {
            levelCount = level;
        }
        for (int i = level - 1; i >= 0; i--) {
            while (p.nextNodes[i] != null && p.nextNodes[i].data.compareTo(element) <= 0) {
                p = p.nextNodes[i];
            }
            Node<T> nextNode = p.nextNodes[i];
            p.nextNodes[i] = newNode;
            if (nextNode != null) {
                newNode.nextNodes[i] = nextNode;
            }
        }
    }

    public void delete(T element) {
        Node<T> p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.nextNodes[i] != null && p.nextNodes[i].data.compareTo(element) < 0) {
                p = p.nextNodes[i];
            }
            Node<T> tmp = p;
            if(tmp.nextNodes[i] !=null && tmp.nextNodes[i].data==element){
                tmp.nextNodes[i] = tmp.nextNodes[i].nextNodes[i];
            }
        }
    }

    // 理论来讲,每两个提取一个的时候，一级索引中元素个数应该占原始数据的 50%，二级索引中元素个数占 25%，三级索引12.5% ，一直到最顶层。
    // 因为这里每一层的晋升概率是 50%。对于每一个新插入的节点，都需要调用 randomLevel 生成一个合理的层数。
    // 该 randomLevel 方法会随机生成 1~MAX_LEVEL 之间的数，且 ：
    //        50%的概率返回 1
    //        25%的概率返回 2
    //      12.5%的概率返回 3 ...
    private int randomLevel() {
        int level = 1;
        while (Math.random() < SKIPLIST_P && level < MAX_LEVEL)
            level += 1;
        return level;
    }

    public void printAll() {
        for (int i = levelCount - 1; i >= 0; i--) {
            System.out.print(head);
            Node<T> p = head.nextNodes[i];
            System.out.print(p);
            while (p != null && p.nextNodes[i] != null) {
                System.out.print(p.nextNodes[i]);
                p = p.nextNodes[i];
            }
            System.out.println();
        }
    }

    static class Node<T> {
        /**
         * 每一层的该节点的后继，不同层数的后继不同
         */
        T data;
        private final Node<T>[] nextNodes = new Node[MAX_LEVEL];
        private int maxLevel = 0;

        @Override
        public String toString() {

            return "{ data: " +
                    data +
                    "; levels: " +
                    maxLevel +
                    " }";
        }
    }

}
