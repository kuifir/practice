package com.kuifir.graph.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class AdjacencyListGraphTest {
    private AdjacencyListGraph<String, Integer> adjacencyListUDG = new AdjacencyListGraph<>(true, 10);
    private AdjacencyListGraph<String, Integer> adjacencyListDG = new AdjacencyListGraph<>(false, 10);

    @BeforeEach
    void beforeEach() throws Exception {
        Stream.of("V1", "V2", "V3", "V4", "V5").forEach(adjacencyListUDG::insertVex);
        Stream.of("V1", "V2", "V3", "V4", "V5", "V6").forEach(adjacencyListDG::insertVex);
        List<String[]> data = Arrays.asList(
                new String[]{"V1", "V2", "1"},
                new String[]{"V1", "V4", "1"},
                new String[]{"V2", "V3", "1"},
                new String[]{"V2", "V5", "1"},
                new String[]{"V3", "V5", "1"},
                new String[]{"V3", "V4", "1"}
        );
        for (String[] strings : data) {
            adjacencyListUDG.insertArc(strings[0], strings[1], Integer.valueOf(strings[2]));
        }
        data = Arrays.asList(
                new String[]{"V1", "V2", "5"},
                new String[]{"V1", "V4", "7"},
                new String[]{"V2", "V3", "4"},
                new String[]{"V3", "V1", "8"},
                new String[]{"V3", "V6", "9"},
                new String[]{"V4", "V3", "5"},
                new String[]{"V4", "V6", "6"},
                new String[]{"V5", "V4", "5"},
                new String[]{"V6", "V1", "3"},
                new String[]{"V6", "V5", "1"}
        );
        for (String[] strings : data) {
            adjacencyListDG.insertArc(strings[0], strings[1], Integer.valueOf(strings[2]));
        }

    }

    @Test
    void insertVex() {
        System.out.println(adjacencyListDG);
        System.out.println(adjacencyListUDG);
    }

    @Test
    void deleteVex() throws Exception {
        adjacencyListUDG.deleteArc("V2", "V3");
        adjacencyListDG.deleteArc("V2", "V3");
        System.out.println(adjacencyListDG);
        System.out.println(adjacencyListUDG);
    }

    @Test
    void dfs() {
        adjacencyListUDG.dfsTraverse();
        System.out.println();
        adjacencyListDG.dfsTraverse();
    }

    @Test
    void bfs() {
        adjacencyListUDG.bfsTraveres();
        adjacencyListDG.bfsTraveres();
    }

    @Test
    void dfsPath() throws Exception {
        adjacencyListUDG.dfsPath("V1", "V5");
        System.out.println();
        adjacencyListDG.dfsPath("V1", "V5");
        System.out.println();
        adjacencyListDG.dfsPath("V6", "V3");
        System.out.println();
        adjacencyListDG.dfsPath("V1", "V6");
        System.out.println();
        adjacencyListDG.dfsPath("V1", "V3");
        System.out.println();
    }

    @Test
    void bfsPath() throws Exception {
        adjacencyListUDG.bfsPath("V1", "V5");
        System.out.println();
        adjacencyListDG.bfsPath("V1", "V5");
        System.out.println();
        adjacencyListDG.bfsPath("V6", "V3");
        System.out.println();
        adjacencyListDG.bfsPath("V1", "V6");
        System.out.println();
        adjacencyListDG.bfsPath("V1", "V3");
        System.out.println();
    }

    @Test
    void printJointPoint() throws Exception {
        adjacencyListDG.printJointPoint();
        System.out.println();
        adjacencyListUDG = new AdjacencyListGraph<>(true, 10);
        Stream.of("a", "b", "c", "d", "e", "f", "g", "h").forEach(adjacencyListUDG::insertVex);
        List<String[]> data = Arrays.asList(
                new String[]{"a", "b", "1"},
                new String[]{"a", "f", "1"},
                new String[]{"a", "g", "1"},
                new String[]{"a", "h", "1"},
                new String[]{"b", "c", "1"},
                new String[]{"f", "c", "1"},
                new String[]{"c", "d", "1"},
                new String[]{"c", "e", "1"},
                new String[]{"g", "h", "1"},
                new String[]{"d", "e", "1"}
        );
        for (String[] strings : data) {
            adjacencyListUDG.insertArc(strings[0], strings[1], Integer.valueOf(strings[2]));
        }
        adjacencyListUDG.printJointPoint();
        System.out.println();
    }

    @Test
    void topologicalSort() throws Exception {

        adjacencyListDG = new AdjacencyListGraph<>(false, 100);

        Stream.of("C1", "C2", "C3", "C4", "C5", "C6",
                "C7", "C8", "C9", "C10", "C11", "C12").forEach(adjacencyListDG::insertVex);
        List<String[]> data = Arrays.asList(
                new String[]{"C1", "C12", "1"},
                new String[]{"C1", "C4", "1"},
                new String[]{"C1", "C3", "1"},
                new String[]{"C1", "C2", "1"},
                new String[]{"C2", "C3", "1"},
                new String[]{"C3", "C5", "1"},
                new String[]{"C3", "C7", "1"},
                new String[]{"C3", "C8", "1"},
                new String[]{"C4", "C5", "1"},
                new String[]{"C5", "C7", "1"},
                new String[]{"C6", "C8", "1"},
                new String[]{"C9", "C10", "1"},
                new String[]{"C9", "C11", "1"},
                new String[]{"C9", "C12", "1"},
                new String[]{"C10", "C12", "1"},
                new String[]{"C11", "C6", "1"}
        );
        for (String[] strings : data) {
            adjacencyListDG.insertArc(strings[0], strings[1], Integer.valueOf(strings[2]));
        }
        adjacencyListDG.topologicalSort();
    }
}