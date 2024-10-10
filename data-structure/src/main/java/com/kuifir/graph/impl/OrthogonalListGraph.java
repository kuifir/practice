package com.kuifir.graph.impl;

import com.kuifir.graph.Graph;

/**
 * 有向图的十字链表存储结构
 *
 * @param <T>
 * @param <A>
 */
public class OrthogonalListGraph<T, A extends Comparable<A>> implements Graph<T> {
    private final int maxVexNum;

    VertexNode<T, A>[] vertices;
    private int vexNum;
    private int arcNum;

    public OrthogonalListGraph(int maxVexNum) {
        this.maxVexNum = maxVexNum;
        vertices = new VertexNode[maxVexNum];
    }

    @Override
    public boolean isUndirectedGraph() {
        return false;
    }

    @Override
    public int locateVex(T vex) throws Exception {
        for (int i = 0; i < vexNum; i++) {
            if (vertices[i].data.equals(vex)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public T firstAdjVex(T vex) throws Exception {
        int i = locateVex(vex);
        if (i > -1) {
            ArcBox<A> firstArc = vertices[i].firstOut;
            if (firstArc != null) {
                return vertices[firstArc.headVex].data;
            }
        }
        return null;
    }

    @Override
    public T nextAdjVex(T vex, T w) throws Exception {
        int i = locateVex(vex);
        if (i > -1) {
            ArcBox<A> arcNode = vertices[i].firstOut;
            while (arcNode != null && !vertices[arcNode.headVex].data.equals(w)) {
                arcNode = arcNode.tailLink;
            }
            if (arcNode != null) {
                if (arcNode.tailLink != null) {
                    return vertices[arcNode.tailLink.headVex].data;
                }
            }
        }
        return null;
    }

    @Override
    public void insertVex(T vex) {
        if (vexNum == maxVexNum - 1) {
            throw new RuntimeException("超出最大顶点数量");
        }
        VertexNode<T, A> newNode = new VertexNode<>();
        newNode.data = vex;
        vertices[vexNum] = newNode;
        vexNum++;
    }

    @Override
    public void deleteVex(T vex) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * 暂未支持增加相同的弧
     * @param v 图中的顶点
     * @param w 图中的另一个顶点
     * @param weight
     * @throws Exception
     */
    @Override
    public void insertArc(T v, T w, Comparable<?> weight) throws Exception {
        int i = locateVex(v);
        int j = locateVex(w);
        if (i > -1 && j > -1) {
            ArcBox<A> arcNode = new ArcBox<>();
            arcNode.info = (A) weight;
            arcNode.tailVex = i;
            arcNode.headVex = j;
            if (vertices[i].firstOut == null) {
                vertices[i].firstOut = arcNode;
            } else {
                ArcBox<A> outArc = vertices[i].firstOut;
                while (outArc.tailLink != null) {
                    outArc = outArc.tailLink;
                }
                outArc.tailLink = arcNode;
            }
            if (vertices[j].firstIn == null) {
                vertices[j].firstIn = arcNode;
            } else {
                ArcBox<A> inArc = vertices[j].firstIn;
                while (inArc.headLink != null) {
                    inArc = inArc.headLink;
                }
                inArc.headLink = arcNode;
            }
            arcNum++;
        }
    }

    @Override
    public void deleteArc(T v, T w) throws Exception {
        int i = locateVex(v);
        int j = locateVex(w);
        if (i > -1 && j > -1) {
            // 删除出弧
            ArcBox<A> outArc = vertices[i].firstOut;
            ArcBox<A> preOutArc = null;
            while (outArc != null && !vertices[outArc.headVex].data.equals(w)) {
                preOutArc = outArc;
                outArc = outArc.tailLink;
            }
            if (outArc != null) {
                if (outArc == vertices[i].firstOut) {
                    vertices[i].firstOut = outArc.tailLink;
                } else {
                    preOutArc.tailLink = outArc.tailLink;
                }
            } else {
                return;
            }
            // 删除入弧
            // 删除出弧
            ArcBox<A> inArc = vertices[j].firstIn;
            ArcBox<A> preInArc = null;
            while (inArc != null && !vertices[inArc.tailVex].data.equals(v)) {
                preInArc = inArc;
                inArc = inArc.headLink;
            }
            if (inArc != null) {
                if (inArc == vertices[j].firstIn) {
                    vertices[j].firstIn = inArc.headLink;
                } else {
                    preInArc.headLink = inArc.headLink;
                }
            }
            arcNum--;
        }
    }

    @Override
    public void dfsTraverse() {

    }

    @Override
    public void bfsTraveres() {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("OrthogonalListGraph{" +
                " vexNum=" + vexNum +
                ", arcNum=" + arcNum);
        if (vexNum <= 0) {
            builder.append("vertices = []");
        } else {
            builder.append("[");
            for (int i = 0; i < vexNum; i++) {
                builder.append(vertices[i]);
                builder.append("\n");
            }
            builder.append("]");
        }
        builder.append('}');
        return builder.toString();
    }

    /**
     * 顶点节点
     * @param <T>
     * @param <A>
     */
    class VertexNode<T, A extends Comparable<A>> {
        T data;
        // 指向该顶点第一条入弧
        ArcBox<A> firstIn;
        // 指向该顶点第一条出弧
        ArcBox<A> firstOut;

        @Override
        public String toString() {
            return "VertexNode{" +
                    "data=" + data +
                    ", firstOut=[" + firstOut +
                    "], firstIn=[" + firstIn +
                    "}]";
        }
    }

    /**
     * 弧节点
     * @param <A>
     */
    class ArcBox<A extends Comparable<A>> {
        /**
         * 和边相关信息(如：权值)
         */
        A info;
        /**
         * 弧尾在顶点中的位置
         */
        Integer tailVex;
        /**
         * 弧头在顶点中的位置
         */
        Integer headVex;
        /**
         * 弧头相同的下一条弧
         */
        ArcBox<A> headLink;
        /**
         * 弧尾相同的下一条弧
         */
        ArcBox<A> tailLink;

        String toTailLink() {
            StringBuilder builder = new StringBuilder();
            builder.append("(" + "tail=").append(tailVex).append(",head=").append(headVex).append(",info=").append(info).append(")");
            if (tailLink != null) {
                builder.append(tailLink.toTailLink());
            }
            return builder.toString();
        }

        String toHeadLink() {
            StringBuilder builder = new StringBuilder();
            builder.append("(" + "tail=").append(tailVex).append(",head=").append(headVex).append(",info=").append(info).append(")");
            if (headLink != null) {
                builder.append(headLink.toHeadLink());
            }
            return builder.toString();
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("(" + "tail=").append(tailVex).append(",head=").append(headVex).append(",info=").append(info).append(")");
            if (tailLink != null) {
                builder.append(",tailLink = {");
                builder.append(tailLink.toTailLink());
                builder.append("}");
            }
            if (headLink != null) {
                builder.append(",headLink = {");
                builder.append(headLink.toHeadLink());
                builder.append("}");
            }
            return builder.toString();
        }
    }
}
