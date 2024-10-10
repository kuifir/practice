package com.kuifir.graph.impl;

import com.kuifir.graph.Graph;

import java.util.Objects;

/**
 * 无向图的邻接多重表存储结构
 */
public class AdjacencyMultilistGraph<T, A extends Comparable<A>> implements Graph<T> {

    private final Integer maxVexNum;
    private int vexNum;
    private int edgeNum;

    VertexNode<T, A>[] vertexNodes;

    public AdjacencyMultilistGraph(Integer maxVexNum) {
        this.maxVexNum = maxVexNum;
        vertexNodes = new VertexNode[maxVexNum];
    }

    @Override
    public boolean isUndirectedGraph() {
        return true;
    }

    @Override
    public int locateVex(T vex) throws Exception {
        for (int i = 0; i < vexNum; i++) {
            if (vertexNodes[i].data.equals(vex)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public T firstAdjVex(T vex) throws Exception {
        int i = locateVex(vex);
        if (i > -1) {
            EdgeBox<A> firstEdge = vertexNodes[i].firstEdge;
            if (Objects.nonNull(firstEdge)) {
                if (firstEdge.iVertex.equals(i)) {
                    return vertexNodes[firstEdge.jVertex].data;
                }
            }
        }
        return null;
    }

    @Override
    public T nextAdjVex(T vex, T w) throws Exception {
        int i = locateVex(vex);
        if (i > -1) {
            EdgeBox<A> edgeBox = vertexNodes[i].firstEdge;
            if (Objects.nonNull(edgeBox)) {
                if (edgeBox.iVertex.equals(i)) {
                    while (edgeBox != null && vertexNodes[edgeBox.jVertex] != w) {
                        edgeBox = edgeBox.iLink;
                    }
                    if (edgeBox != null) {
                        if (edgeBox.iLink != null) {
                            return vertexNodes[edgeBox.iLink.jVertex].data;
                        }
                    }
                } else if (edgeBox.jVertex.equals(i)) {
                    while (edgeBox != null && vertexNodes[edgeBox.iVertex] != w) {
                        edgeBox = edgeBox.jLink;
                    }
                    if (edgeBox != null) {
                        if (edgeBox.jLink != null) {
                            return vertexNodes[edgeBox.jLink.iVertex].data;
                        }
                    }
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
        vertexNodes[vexNum] = newNode;
        vexNum++;
    }

    @Override
    public void deleteVex(T vex) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * 暂未支持添加相同的边
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
            EdgeBox<A> newEdge = new EdgeBox<>();
            newEdge.info = (A) weight;
            newEdge.iVertex = i;
            newEdge.jVertex = j;
            if (vertexNodes[i].firstEdge == null) {
                vertexNodes[i].firstEdge = newEdge;
            } else {
                EdgeBox<A> iEdge = vertexNodes[i].firstEdge;
                if (iEdge.iVertex.equals(i)) {
                    while (iEdge.iLink != null) {
                        iEdge = iEdge.iLink;
                    }
                    iEdge.iLink = newEdge;
                } else {
                    while (iEdge.jLink != null) {
                        iEdge = iEdge.jLink;
                    }
                    iEdge.jLink = newEdge;
                }
            }
            if (vertexNodes[j].firstEdge == null) {
                vertexNodes[j].firstEdge = newEdge;
            } else {
                EdgeBox<A> jEdge = vertexNodes[j].firstEdge;
                if (jEdge.iVertex.equals(j)) {
                    while (jEdge.iLink != null) {
                        jEdge = jEdge.iLink;
                    }
                    jEdge.iLink = newEdge;
                } else {
                    while (jEdge.jLink != null) {
                        jEdge = jEdge.jLink;
                    }
                    jEdge.jLink = newEdge;
                }
            }
            edgeNum++;
        }
    }

    @Override
    public void deleteArc(T v, T w) throws Exception {
        int i = locateVex(v);
        int j = locateVex(w);
        if (i > -1 && j > -1) {
            EdgeBox<A> iEdge = vertexNodes[i].firstEdge;
            EdgeBox<A> iPreEdge = null;
            // 删除v节点链表的数据
            if (iEdge.iVertex.equals(i)) {
                while (iEdge != null && iEdge.jVertex != j) {
                    iPreEdge = iEdge;
                    iEdge = iEdge.iLink;
                }
                if (iEdge != null) {
                    if (iEdge == vertexNodes[i].firstEdge) {
                        vertexNodes[i].firstEdge = iEdge.iLink;
                    } else {
                        iPreEdge.iLink = iEdge.iLink;
                    }
                }
            } else {
                while (iEdge != null && iEdge.iVertex != j) {
                    iPreEdge = iEdge;
                    iEdge = iEdge.jLink;
                }
                if (iEdge != null) {
                    if (iEdge == vertexNodes[i].firstEdge) {
                        vertexNodes[i].firstEdge = iEdge.jLink;
                    } else {
                        iPreEdge.jLink = iEdge.jLink;
                    }
                }
            }
            // 删除v节点的数据
            EdgeBox<A> jEdge = vertexNodes[j].firstEdge;
            EdgeBox<A> jPreEdge = null;
            // 删除v节点链表的数据
            if (jEdge.iVertex.equals(j)) {
                while (iEdge != null && iEdge.jVertex != i) {
                    jPreEdge = jEdge;
                    jEdge = jEdge.iLink;
                }
                if (jEdge != null) {
                    if (jEdge == vertexNodes[j].firstEdge) {
                        vertexNodes[j].firstEdge = jPreEdge.iLink;
                    } else {
                        jPreEdge.iLink = jEdge.iLink;
                    }
                }
            } else {
                while (jEdge != null && jEdge.iVertex != i) {
                    jPreEdge = jEdge;
                    jEdge = jEdge.jLink;
                }
                if (jEdge != null) {
                    if (jEdge == vertexNodes[j].firstEdge) {
                        vertexNodes[j].firstEdge = jPreEdge.jLink;
                    } else {
                        jPreEdge.jLink = jEdge.jLink;
                    }
                }
            }
        }
    }

    @Override
    public void dfsTraverse() {

    }

    @Override
    public void bfsTraveres() {

    }

    /**
     * 边节点
     */
    class EdgeBox<A extends Comparable<A>> {
        /**
         * 标记访问
         */
        Boolean mark;

        /**
         * 该边依附的两个顶点的位置
         */
        Integer iVertex;
        Integer jVertex;
        /**
         * 依附i顶点的下一条边
         */
        EdgeBox<A> iLink;
        /**
         * 依附j顶点的下一条边
         */
        EdgeBox<A> jLink;

        /**
         * 该边信息指针
         */
        A info;
    }

    /**
     * 顶点节点
     */
    class VertexNode<T, A extends Comparable<A>> {
        T data;
        /**
         * 指向第一条依附该顶点的边
         */
        EdgeBox<A> firstEdge;
    }
}
