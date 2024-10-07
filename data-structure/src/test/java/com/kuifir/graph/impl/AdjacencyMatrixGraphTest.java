package com.kuifir.graph.impl;

class AdjacencyMatrixGraphTest {


    public static void main(String[] args) {
        // 创建有向网
//        AdjacencyMatrixGraph adjacencyMatrixGraph = new AdjacencyMatrixGraph(false, 6, 10);
//        System.out.println(adjacencyMatrixGraph);
        // 创建无向网

        AdjacencyMatrixGraph adjacencyMatrixGraph2 = new AdjacencyMatrixGraph(true, 5, 6);
        System.out.println(adjacencyMatrixGraph2);
    }
}