package com.kuifir.graph.impl;

import com.kuifir.graph.Graph;

/**
 * 无向图的临界多重表存储结构
 */
public class AdjacencyMultilistGraph<T,A> implements Graph<T> {
    @Override
    public boolean isUndirectedGraph() {
        return true;
    }

    @Override
    public int locateVex(T vex) throws Exception {
        return 0;
    }

    @Override
    public T firstAdjVex(T vex) throws Exception {
        return null;
    }

    @Override
    public T nextAdjVex(T vex, T w) throws Exception {
        return null;
    }

    @Override
    public void insertVex(T vex) {

    }

    @Override
    public void deleteVex(T vex) throws Exception {

    }

    @Override
    public void insertArc(T v, T w, Comparable<?> weight) throws Exception {

    }

    @Override
    public void deleteArc(T v, T w) throws Exception {

    }

    @Override
    public void dfsTraverse() {

    }

    @Override
    public void bfsTraveres() {

    }
}
