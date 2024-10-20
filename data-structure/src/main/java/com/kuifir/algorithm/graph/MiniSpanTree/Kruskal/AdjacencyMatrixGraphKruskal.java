package com.kuifir.algorithm.graph.MiniSpanTree.Kruskal;

import com.kuifir.graph.impl.AdjacencyMatrixGraph;

import java.util.*;
import java.util.stream.Collectors;

public class AdjacencyMatrixGraphKruskal {
    public static void main(String[] args) throws Exception {
        String[] vexs = {"V1", "V2", "V3", "V4", "V5", "V6"};
        Integer[][] arcs = {
                {0, 6, 1, 5, 0, 0},
                {6, 0, 5, 0, 3, 0},
                {1, 5, 0, 5, 6, 4},
                {5, 0, 5, 0, 0, 2},
                {0, 3, 6, 0, 0, 6},
                {0, 0, 4, 2, 6, 0}
        };
        AdjacencyMatrixGraph adjacencyMatrixGraph = new AdjacencyMatrixGraph(true, vexs, arcs, 20);
        System.out.println(adjacencyMatrixGraph);
        AdjacencyMatrixGraph miniSpanTree = miniSpanTree(adjacencyMatrixGraph);
        System.out.println();
        System.out.println(miniSpanTree);
    }

    public static AdjacencyMatrixGraph miniSpanTree(AdjacencyMatrixGraph graph) throws Exception {
        Integer[][] arcs = graph.getArcs();
        String[] vexs = graph.getVexs();
        String vex = vexs[0];
        AdjacencyMatrixGraph miniSpanTree = new AdjacencyMatrixGraph(true, vexs);
        // 连通分量
        int[] vexSet = new int[graph.getVexNum()];
        for (int i = 0; i < vexs.length; i++) {
            vexSet[i] = i;
        }
        // 获取边信息，并对边进行排序
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < arcs.length; i++) {
            for (int j = 0; j < arcs[i].length; j++) {
                if (arcs[i][j] > 0) {
                    Edge edge = new Edge(i, j, arcs[i][j]);
                    edges.add(edge);
                }
            }
        }
        List<Edge> sortedEdges = edges.stream().sorted(Comparator.comparing(Edge::getWeight)).collect(Collectors.toList());
        while (miniSpanTree.getArcNum() < 2 * (miniSpanTree.getVexNum() - 1))
            for (Edge sortedEdge : sortedEdges) {
                // 不在同一连通分量里，则添加边
                if (!Objects.equals(vexSet[sortedEdge.getHead()], vexSet[sortedEdge.getTail()])) {
                    miniSpanTree.insertArc(vexs[sortedEdge.getHead()], vexs[sortedEdge.getTail()], sortedEdge.getWeight());
                    System.out.println("(" + vexs[sortedEdge.getHead()] + "," + vexs[sortedEdge.getTail()] + "," + sortedEdge.getWeight() + ")");
                    int i1 = vexSet[sortedEdge.getTail()];
                    int i2 = vexSet[sortedEdge.getHead()];
                    for (int i = 0; i < vexSet.length; i++) {
                        if (vexSet[i] == i1) {
                            vexSet[i] = i2;
                        }
                    }
                    System.out.println(Arrays.toString(vexSet));
                }
            }
        return miniSpanTree;
    }

    static class Edge {
        Integer head;
        Integer tail;
        Integer weight;

        public Edge(Integer head, Integer tail, Integer weight) {
            this.head = head;
            this.tail = tail;
            this.weight = weight;
        }

        public Integer getHead() {
            return head;
        }

        public Integer getTail() {
            return tail;
        }

        public Integer getWeight() {
            return weight;
        }
    }
}
