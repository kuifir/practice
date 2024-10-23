package com.kuifir.graph.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class AdjacencyListGraphIntegerWeightTest {
    private AdjacencyListGraphIntegerWeight<String> graph = new AdjacencyListGraphIntegerWeight(false,100);

    @BeforeEach
    void beforeEach() throws Exception {
        Stream.of("V0","V1", "V2", "V3", "V4", "V5", "V6",
                "V7", "V8").forEach(graph::insertVex);
        List<String[]> data = Arrays.asList(
                new String[]{"V0", "V1", "6"},
                new String[]{"V0", "V2", "4"},
                new String[]{"V0", "V3", "5"},
                new String[]{"V1", "V4", "1"},
                new String[]{"V2", "V4", "1"},
                new String[]{"V3", "V5", "2"},
                new String[]{"V4", "V6", "9"},
                new String[]{"V4", "V7", "7"},
                new String[]{"V5", "V7", "4"},
                new String[]{"V6", "V8", "2"},
                new String[]{"V7", "V8", "4"}
        );
        for (String[] strings : data) {
            graph.insertArc(strings[0], strings[1], Integer.valueOf(strings[2]));
        }
    }

    @Test
    public void criticalPath() throws Exception {
        graph.criticalPath();
    }

}