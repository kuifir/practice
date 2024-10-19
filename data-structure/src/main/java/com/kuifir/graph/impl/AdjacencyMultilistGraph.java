package com.kuifir.graph.impl;

import com.kuifir.graph.Graph;

import java.util.*;

/**
 * 无向图的邻接多重表存储结构
 */
public class AdjacencyMultilistGraph<T, A extends Comparable<A>> implements Graph<T> {

    private final Integer maxVexNum;
    private int vexNum;
    private int edgeNum;

    VertexNode<T, A>[] vertexNodes;
    private boolean[] visited;

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
                } else {
                    return vertexNodes[firstEdge.iVertex].data;
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
                while (edgeBox != null && !vertexNodes[edgeBox.jVertex].data.equals(w) && !vertexNodes[edgeBox.iVertex].data.equals(w)) {
                    if (edgeBox.iVertex.equals(i)) {
                        edgeBox = edgeBox.iLink;
                    } else {
                        edgeBox = edgeBox.jLink;
                    }
                }
                if (edgeBox != null) {
                    if (edgeBox.iVertex.equals(i)) {
                        if (edgeBox.iLink != null) {
                            return edgeBox.iLink.iVertex.equals(i) ? vertexNodes[edgeBox.iLink.jVertex].data : vertexNodes[edgeBox.iLink.iVertex].data;
                        }
                    } else {
                        if (edgeBox.jLink != null) {
                            return edgeBox.jLink.iVertex.equals(i) ? vertexNodes[edgeBox.jLink.jVertex].data : vertexNodes[edgeBox.jLink.iVertex].data;
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
     *
     * @param v      图中的顶点
     * @param w      图中的另一个顶点
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
//            if (vertexNodes[i].firstEdge == null && vertexNodes[j].firstEdge == null) {
//                newEdge.iVertex = i;
//                newEdge.jVertex = j;
//            } else if (vertexNodes[i].firstEdge != null) {
//                if (vertexNodes[i].firstEdge.iVertex == i) {
//                    newEdge.iVertex = i;
//                    newEdge.jVertex = j;
//                } else {
//                    newEdge.iVertex = j;
//                    newEdge.jVertex = i;
//                }
//            } else {
//                if (vertexNodes[j].firstEdge.iVertex == j) {
//                    newEdge.iVertex = j;
//                    newEdge.jVertex = i;
//                } else {
//                    newEdge.iVertex = i;
//                    newEdge.jVertex = j;
//                }
//            }

            if (vertexNodes[i].firstEdge == null) {
                vertexNodes[i].firstEdge = newEdge;
            } else {
                EdgeBox<A> iEdge = vertexNodes[i].firstEdge;
                EdgeBox<A> iPre = null;
                while (iEdge != null) {
                    iPre = iEdge;
                    if (iEdge.iVertex.equals(i)) {
                        iEdge = iEdge.iLink;
                    } else {
                        iEdge = iEdge.jLink;
                    }
                }
                if (iPre.iVertex.equals(i)) {
                    iPre.iLink = newEdge;
                } else {
                    iPre.jLink = newEdge;
                }
            }
            if (vertexNodes[j].firstEdge == null) {
                vertexNodes[j].firstEdge = newEdge;
            } else {
                EdgeBox<A> jEdge = vertexNodes[j].firstEdge;
                EdgeBox<A> jPre = null;
                while (jEdge != null) {
                    jPre = jEdge;
                    if (jEdge.iVertex.equals(j)) {
                        jEdge = jEdge.iLink;
                    } else {
                        jEdge = jEdge.jLink;
                    }
                }
                if (jPre.iVertex.equals(j)) {
                    jPre.iLink = newEdge;
                } else {
                    jPre.jLink = newEdge;
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
            if (iEdge != null) {
                // 删除v节点链表的数据
                while (iEdge != null && iEdge.iVertex != j && iEdge.jVertex != j) {
                    iPreEdge = iEdge;
                    if (iEdge.iVertex.equals(i)) {
                        iEdge = iEdge.iLink;
                    } else {
                        iEdge = iEdge.jLink;
                    }
                }
                if (Objects.isNull(iPreEdge)) {
                    if (iEdge.iVertex.equals(j)) {
                        vertexNodes[i].firstEdge = iEdge.iLink;
                    } else {
                        vertexNodes[i].firstEdge = iEdge.jLink;
                    }
                } else {
                    if (iEdge != null) {
                        if (iPreEdge.iVertex.equals(i)) {
                            if (iEdge.iVertex.equals(i)) {
                                iPreEdge.iLink = iEdge.iLink;
                            } else {
                                iPreEdge.iLink = iEdge.jLink;
                            }
                        } else {
                            if (iEdge.iVertex.equals(i)) {
                                iPreEdge.jLink = iEdge.iLink;
                            } else {
                                iPreEdge.jLink = iEdge.jLink;
                            }
                        }
                    }
                }
            }
            // 删除v节点的数据
            EdgeBox<A> jEdge = vertexNodes[j].firstEdge;
            EdgeBox<A> jPreEdge = null;
            // 删除v节点链表的数据
            if (jEdge != null) {
                // 删除v节点链表的数据
                while (jEdge != null && jEdge.iVertex != i && jEdge.jVertex != i) {
                    jPreEdge = jEdge;
                    if (jEdge.iVertex.equals(j)) {
                        jEdge = jEdge.iLink;
                    } else {
                        jEdge = jEdge.jLink;
                    }
                }
                if (Objects.isNull(jPreEdge)) {
                    if (jEdge.iVertex.equals(i)) {
                        vertexNodes[j].firstEdge = jEdge.iLink;
                    } else {
                        vertexNodes[j].firstEdge = jEdge.jLink;
                    }
                } else {
                    if (jEdge != null) {
                        if (jPreEdge.iVertex.equals(j)) {
                            if (jEdge.iVertex.equals(j)) {
                                jPreEdge.iLink = jEdge.iLink;
                            } else {
                                jPreEdge.iLink = jEdge.jLink;
                            }
                        } else {
                            if (jEdge.iVertex.equals(j)) {
                                jPreEdge.jLink = jEdge.iLink;
                            } else {
                                jPreEdge.jLink = jEdge.jLink;
                            }
                        }
                    }
                }
            }
            edgeNum--;
        }
    }

    @Override
    public void dfsTraverse() throws Exception {
        visited = new boolean[vexNum];
        for (int i = 0; i < vexNum; i++) {
            if (!visited[i]) {
                dfs_AM(i);
                System.out.println();
            }
        }
    }

    void dfs_AM(int v) throws Exception {
        VertexNode<T, A> vertexNode = vertexNodes[v];
        System.out.print(vertexNode.data + " ");
        visited[v] = true;
        for (T t = firstAdjVex(vertexNode.data); t != null; t = nextAdjVex(vertexNode.data, t)) {
            int i = locateVex(t);
            if (!visited[i]) {
                dfs_AM(i);
            }
        }
    }

    @Override
    public void bfsTraveres() throws Exception {
        visited = new boolean[vexNum];
        for (int i = 0; i < vexNum; i++) {
            if (!visited[i]) {
                bfs_AM(i);
                System.out.println();
            }
        }
    }

    @Override
    public void dfsPath(T v, T w) throws Exception {
        int i = locateVex(v);
        int j = locateVex(w);
        if (i > -1 && j > -1) {
            visited = new boolean[vexNum];
            dfPath(v, w, new LinkedList<>());
        } else {
            System.out.println("路径不存在");
        }
    }

    private void dfPath(T v, T w, LinkedList<T> path) throws Exception {
        int i = locateVex(v);
        path.add(v);
        visited[i] = true;
        VertexNode<T, A> vertexNode = vertexNodes[i];
        for (T t = firstAdjVex(vertexNode.data); t != null; t = nextAdjVex(vertexNode.data, t)) {
            int j = locateVex(t);
            if (!visited[j]) {
                if (vertexNodes[j].data.equals(w)) {
                    visited[j] = true;
                    path.add(vertexNodes[j].data);
                    System.out.println(path);
                } else {
                    dfPath(vertexNodes[j].data, w, path);
                }
                visited[j] = false;
                path.removeLast();
            }
        }
    }

    @Override
    public void bfsPath(T v, T w) throws Exception {
        int i = locateVex(v);
        int j = locateVex(w);
        if (i > -1 && j > -1) {
            visited = new boolean[vexNum];
            bfPath(v, w, new LinkedList<>());
        } else {
            System.out.println("路径不存在");
        }
    }

    private void bfPath(T v, T w, LinkedList<T> path) throws Exception {
        int j = locateVex(v);
        path.add(v);
        List<Pair<Integer, Integer>> queue = new ArrayList<>();
        queue.add(new Pair<>(null, j));
        for (int tmp = 0; tmp < queue.size(); tmp++) {
            Pair<Integer, Integer> pair = queue.get(tmp);
            VertexNode<T, A> vertex = vertexNodes[pair.b];
            visited[pair.b] = true;
            for (T vex = this.firstAdjVex(vertex.data); vex != null; vex = nextAdjVex(vertex.data, vex)) {
                int i = locateVex(vex);
                if (!visited[i]) {
                    visited[i] = true;
                    queue.add(new Pair<>(tmp, i));
                    if (vertexNodes[i].data.equals(w)) {
                        printBFPath(queue, queue.size() - 1);
                        System.out.println();
                    }
                }
            }
        }
    }
    private void printBFPath(List<Pair<Integer, Integer>> queue, int v) {
        if (!queue.isEmpty()) {
            Pair<Integer, Integer> pair = queue.get(v);
            if (pair.a != null) {
                printBFPath(queue, pair.a);
            }
            System.out.print(vertexNodes[pair.b].data + ",");
        }
    }
    private void bfs_AM(int v) throws Exception {
        System.out.print(vertexNodes[v].data + " ");
        visited[v] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(v);
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (T vex = this.firstAdjVex(vertexNodes[poll].data); vex != null; vex = nextAdjVex(vertexNodes[poll].data, vex)) {
                int i = locateVex(vex);
                if (!visited[i]) {
                    System.out.print(vertexNodes[i].data + " ");
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
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
