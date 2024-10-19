package com.kuifir.algorithm.graph.MiniSpanTree.Prim;

import com.kuifir.graph.impl.AdjacencyMatrixGraph;
import com.kuifir.graph.impl.Pair;

import java.util.Objects;

public class AdjacencyMatrixGraphPrim {
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
        Pair<String, Integer>[] closedge = new Pair[graph.getVexNum()];
        AdjacencyMatrixGraph miniSpanTree = new AdjacencyMatrixGraph(true, vexs);
        for (int i = 0; i < closedge.length; i++) {
            if (arcs[0][i] != 0) {
                closedge[i] = new Pair<>(vex, arcs[0][i]);
            } else if (i != 0) {
                closedge[i] = new Pair<>(vex, Integer.MAX_VALUE);
            } else {
                closedge[i] = new Pair<>(vex, 0);
            }
        }
        for (int i = 1; i < graph.getVexNum(); i++) {
            int minPair = getMinPair(closedge);
            miniSpanTree.insertArc(closedge[minPair].getA(), vexs[minPair], closedge[minPair].getB());
            System.out.println("(" + closedge[minPair].getA() + "," + vexs[minPair] + ")");
            closedge[minPair].setB(0);
            for (int j = 0; j < graph.getVexNum(); j++) {
                if (arcs[minPair][j].compareTo(0) > 0 && arcs[minPair][j].compareTo(closedge[j].getB()) < 0) {
                    closedge[j] = new Pair<>(vexs[minPair], arcs[minPair][j]);
                }
            }
        }
        return miniSpanTree;
    }

    private static int getMinPair(Pair<String, Integer>[] closedge) {
        Pair<String, Integer> pair = null;
        int min = -1;
        for (int i1 = 0; i1 < closedge.length; i1++) {
            Pair<String, Integer> tmp = closedge[i1];
            if (Objects.nonNull(tmp) && tmp.getB().compareTo(0) > 0) {
                if (Objects.isNull(pair)) {
                    pair = closedge[i1];
                    min = i1;
                } else if (pair.getB().compareTo(tmp.getB()) > 0) {
                    pair = tmp;
                    min = i1;
                }
            }
        }
        return min;
    }
}
