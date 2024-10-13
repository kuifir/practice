package com.kuifir.graph.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMultilistGraphTest {

    private AdjacencyMultilistGraph<String,Integer> adjacencyMultilistGraph = new AdjacencyMultilistGraph<>(10);

    @BeforeEach
    void beforeEach() throws Exception {
        Stream.of("V1", "V2", "V3", "V4", "V5").forEach(adjacencyMultilistGraph::insertVex);
        List<String[]> data = Arrays.asList(
                new String[]{"V1", "V2", "1"},
                new String[]{"V1", "V4", "1"},
                new String[]{"V2", "V3", "1"},
                new String[]{"V2", "V5", "1"},
                new String[]{"V3", "V5", "1"},
                new String[]{"V3", "V4", "1"}
        );
        for (String[] strings : data) {
            adjacencyMultilistGraph.insertArc(strings[0], strings[1], Integer.valueOf(strings[2]));
        }
    }

    @Test
    void insertArc() {
        System.out.println(adjacencyMultilistGraph.isUndirectedGraph());
    }
    @Test
    void dfs() throws Exception {
        adjacencyMultilistGraph.dfsTraverse();
    }
}