package com.kuifir.graph.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdjacencyMatrixGraphTest {
    AdjacencyMatrixGraph adjacencyMatrixGraph;

    @BeforeEach
    void beforeEach() {
        String[] vexs = {"V0", "V1", "V2", "V3", "V4", "V5"};
        Integer[][] arcs = {
                {0, 0, 10, 0, 30, 100},
                {0, 0, 5, 0, 0, 0},
                {0, 0, 0, 50, 0, 0},
                {0, 0, 0, 0, 0, 10},
                {0, 0, 0, 20, 0, 60},
                {0, 0, 0, 0, 0, 0}
        };
        adjacencyMatrixGraph = new AdjacencyMatrixGraph(true, vexs, arcs, 8);
    }

    @Test
    void shortestPath_DIJ() throws Exception {
        adjacencyMatrixGraph.shortestPath_DIJ("V0");
    }


    public static void main(String[] args) throws Exception {
        // 创建有向网
//        AdjacencyMatrixGraph adjacencyMatrixGraph2 = new AdjacencyMatrixGraph(false, 6, 10);
//        System.out.println(adjacencyMatrixGraph);
        // 创建无向网

        AdjacencyMatrixGraph adjacencyMatrixGraph2 = new AdjacencyMatrixGraph(true, 8, 10);
        System.out.println(adjacencyMatrixGraph2);
        adjacencyMatrixGraph2.dfsTraverse();
        System.out.println();
        adjacencyMatrixGraph2.bfsTraveres();
        System.out.println();
//        adjacencyMatrixGraph2.dfsPath("V1","V5");
//        System.out.println();
//        adjacencyMatrixGraph2.dfsPath("V6","V1");
//        System.out.println();
//        adjacencyMatrixGraph2.dfsPath("V1","V4");
//        System.out.println();
//
//        adjacencyMatrixGraph2.bfsPath("V1","V5");
//        System.out.println();
//        adjacencyMatrixGraph2.bfsPath("V6","V1");
//        System.out.println();
//        adjacencyMatrixGraph2.bfsPath("V1", "V4");
//        System.out.println();
        adjacencyMatrixGraph2.dfsPath("0", "7");
        System.out.println();
        adjacencyMatrixGraph2.bfsPath("0", "7");
        System.out.println();
    }
}