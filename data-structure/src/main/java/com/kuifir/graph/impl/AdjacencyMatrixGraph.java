package com.kuifir.graph.impl;

import com.kuifir.graph.Graph;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 图的邻接矩阵存储结构
 * 顶点 不重复 类型为String
 * 权值类型为 Integer
 */
public class AdjacencyMatrixGraph implements Graph<String> {
    private final boolean unDirectedGraphFlag;
    private final int vexNum;
    private int arcNum;

    /**
     * 顶点表
     */
    private String[] vexs;
    /**
     * 邻接矩阵
     */
    private Integer[][] arcs;


    public AdjacencyMatrixGraph(boolean unDirectedGraphFlag, int vexNum, int arcNum) {
        this.unDirectedGraphFlag = unDirectedGraphFlag;
        this.vexNum = vexNum;
        this.arcNum = arcNum;
        vexs = new String[vexNum];
        arcs = new Integer[vexNum][vexNum];
        for (Integer[] arc : arcs) {
            Arrays.fill(arc, 0);
        }
        try (Scanner scanner = new Scanner(System.in)) {
            for (int i = 0; i < vexNum; i++) {
                System.out.println("输入第" + i + "个顶点");
                String s = scanner.nextLine();
                vexs[i] = s;
            }
            for (int i = 0; i < arcNum; ) {
                System.out.println("输入第" + i + "条边，依次输入两个顶点和权值,用','隔开");
                String s = scanner.nextLine();
                String[] split = s.split(",");
                if (split.length != 3) {
                    System.out.println("输入错误，重新输入");
                } else {
                    try {
                        int i1 = locateVex(split[0].trim());
                        int i2 = locateVex(split[1].trim());
                        arcs[i1][i2] = Integer.valueOf(split[2]);
                        if (isUndirectedGraph()) {
                            arcs[i2][i1] = Integer.valueOf(split[2]);
                        }
                        i++;
                    } catch (Exception e) {
                        System.out.println("输入顶点不存在，重新输入");
                    }

                }
            }
        }
    }

    @Override
    public boolean isUndirectedGraph() {
        return unDirectedGraphFlag;
    }

    @Override
    public int locateVex(String vex) throws Exception {
        for (int i = 0; i < arcs.length; i++) {
            if (vexs[i].equals(vex)) {
                return i;
            }
        }
        throw new Exception("顶点不存在");
    }

    @Override
    public String firstAdjVex(String vex) throws Exception {
        int i = locateVex(vex);
        for (int j = 0; j < vexNum; j++) {
            if (arcs[i][j] > 0) {
                return vexs[j];
            }
        }
        return null;
    }

    @Override
    public String nextAdjVex(String vex, String w) throws Exception {
        int i = locateVex(vex);
        int i2 = locateVex(w);
        for (int j = i2 + 1; j < vexNum; j++) {
            if (arcs[i][j] > 0) {
                return vexs[j];
            }
        }
        return null;
    }

    @Override
    public void insertVex(String vex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteVex(String vex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertArc(String v, String w, Comparable<?> weight) throws Exception {
        int i1 = locateVex(v);
        int i2 = locateVex(w);
        arcs[i1][i2] = (Integer) weight;
        if (isUndirectedGraph()) {
            arcs[i2][i1] = (Integer) weight;
        }
    }

    @Override
    public void deleteArc(String v, String w) throws Exception {
        int i1 = locateVex(v);
        int i2 = locateVex(w);
        arcs[i1][i2] = 0;
        if (isUndirectedGraph()) {
            arcs[i2][i1] = 0;
        }
    }

    @Override
    public void dfsTraverse() {
        // todo 连接矩阵存储结构深度优先遍历
    }

    @Override
    public void bfsTraveres() {
        // todo 邻接矩阵存储结构广度优先遍历
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("AdjacencyMatrixGraph{" + "vexs=")
                .append(Arrays.toString(vexs))
                .append(", arcs=");
        if (arcs.length == 0) {
            result.append("{}");
        } else {
            result.append("{\n");
            for (Integer[] arc : arcs) {
                result.append(Arrays.toString(arc));
                result.append("\n");
            }
            result.append("}");
        }
        return result.toString();
    }
}