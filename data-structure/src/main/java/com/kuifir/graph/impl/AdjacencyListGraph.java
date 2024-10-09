package com.kuifir.graph.impl;

import com.kuifir.graph.Graph;

/**
 * 图的邻接表存储结构
 *
 * @param <T> 顶点信息类型
 * @param <A> 边的信息类型
 */
public class AdjacencyListGraph<T, A extends Comparable<A>> implements Graph<T> {

    private final boolean unDirectedGraphFlag;
    private final int maxVexNum;

    VertexNode<T, A>[] vertices;
    private int vexNum;
    private int arcNum;

    public AdjacencyListGraph(boolean unDirectedGraphFlag, int maxVexNum) {
        this.unDirectedGraphFlag = unDirectedGraphFlag;
        this.maxVexNum = maxVexNum;
        vertices = new VertexNode[maxVexNum];
    }

    @Override
    public boolean isUndirectedGraph() {
        return unDirectedGraphFlag;
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
            ArcNode<A> firstArc = vertices[i].firstArc;
            if (firstArc != null) {
                return vertices[firstArc.adjacencyVex].data;
            }
        }
        return null;
    }

    @Override
    public T nextAdjVex(T vex, T w) throws Exception {
        int i = locateVex(vex);
        if (i > -1) {
            ArcNode<A> arcNode = vertices[i].firstArc;
            while (arcNode != null && !vertices[arcNode.adjacencyVex].data.equals(w)) {
                arcNode = arcNode.nextArc;
            }
            if (arcNode != null) {
                if (arcNode.nextArc != null) {
                    return vertices[arcNode.nextArc.adjacencyVex].data;
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

    @Override
    public void insertArc(T v, T w, Comparable<?> weight) throws Exception {
        int i = locateVex(v);
        int j = locateVex(w);
        if (i > -1 && j > -1) {
            ArcNode<A> arcNode = new ArcNode<>();
            arcNode.info = (A) weight;
            arcNode.adjacencyVex = j;
            arcNode.nextArc = vertices[i].firstArc;
            vertices[i].firstArc = arcNode;
            if (isUndirectedGraph()) {
                ArcNode<A> arcNodeJ = new ArcNode<>();
                arcNodeJ.info = (A) weight;
                arcNodeJ.adjacencyVex = i;
                arcNodeJ.nextArc = vertices[j].firstArc;
                vertices[j].firstArc = arcNodeJ;
                arcNum++;
            }
            arcNum++;
        }
    }

    @Override
    public void deleteArc(T v, T w) throws Exception {
        int i = locateVex(v);
        int j = locateVex(w);
        if (i > -1 && j > -1) {
            ArcNode<A> tmpI = vertices[i].firstArc;
            ArcNode<A> preI = null;
            while (tmpI != null && tmpI.adjacencyVex != j) {
                preI = tmpI;
                tmpI = tmpI.nextArc;
            }
            if (tmpI != null) {
                if (tmpI == vertices[i].firstArc) {
                    vertices[i].firstArc = tmpI.nextArc;
                } else {
                    preI.nextArc = tmpI.nextArc;
                }
            }else {
                return;
            }
            if (isUndirectedGraph()) {
                ArcNode<A> tmpJ = vertices[j].firstArc;
                ArcNode<A> preJ = null;
                while (tmpJ != null && tmpJ.adjacencyVex != i) {
                    preJ = tmpJ;
                    tmpJ = tmpJ.nextArc;
                }
                if (tmpJ != null) {
                    if (tmpJ == vertices[j].firstArc) {
                        vertices[j].firstArc = tmpJ.nextArc;
                    } else {
                        preJ.nextArc = tmpJ.nextArc;
                    }
                }
            }
            arcNum--;
        }
    }

    @Override
    public void dfsTraverse() {
        // todo 连接矩阵存储结构深度优先遍历
    }

    @Override
    public void bfsTraveres() {
        // todo 邻接矩阵存储结构广度优先遍历
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("AdjacencyListGraph{" +
                "maxVexNum=" + maxVexNum +
                ", vexNum=" + vexNum +
                ", arcNum=" + arcNum +
                ", " + "\n" + "vertices=");
        if (vexNum > 0) {
            builder.append("[");
            for (int i = 0; i < vexNum; i++) {
                builder.append(vertices[i]);
                builder.append("\n");
            }
            builder.append("]");
        } else {
            builder.append("[]");
        }
        builder.append('}');

        return builder.toString();
    }

    /**
     * 顶点信息
     *
     * @param <T>
     */
    class VertexNode<T, A extends Comparable<A>> {
        T data;
        /**
         * 指向第一条依附该顶点的边的指针
         */
        ArcNode<A> firstArc;

        @Override
        public String toString() {
            return "VertexNode{" +
                    "data=" + data +
                    ", arcs=" + firstArc +
                    '}';
        }
    }

    /**
     * 边节点
     *
     * @param <C>
     */
    class ArcNode<C extends Comparable<C>> {
        /**
         * 该边指向的顶点的位置
         */
        int adjacencyVex;
        /**
         * 和边相关信息(如：权值)
         */
        C info;
        /***
         * 指向下一条边
         */
        ArcNode<C> nextArc;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("(adjacencyVex=" + adjacencyVex + ", info=" + info + ")");
            if (nextArc != null) {
                builder.append(nextArc);
            }
            return builder.toString();
        }
    }
}
