package com.kuifir.graph.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OrthogonalListGraphTest {
    private OrthogonalListGraph<String, Integer> orthogonalListGraph = new OrthogonalListGraph<>(10);

    @BeforeEach
    void beforeEach() throws Exception {
        Stream.of("V1", "V2", "V3", "V4").forEach(orthogonalListGraph::insertVex);
        List<String[]> data = Arrays.asList(
                new String[]{"V1", "V2", "3"},
                new String[]{"V1", "V3", "4"},
                new String[]{"V3", "V1", "40"},
                new String[]{"V3", "V4", "7"},
                new String[]{"V4", "V1", "5"},
                new String[]{"V4", "V2", "6"},
                new String[]{"V4", "V3", "70"}
        );
        for (String[] strings : data) {
            orthogonalListGraph.insertArc(strings[0], strings[1], Integer.valueOf(strings[2]));
        }
    }

    @Test
    void insertArc() {
        System.out.println(orthogonalListGraph);
    }
}