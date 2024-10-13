package com.kuifir.graph;

public interface Graph<T> {

    /**
     * 是否是无向图
     */
    boolean isUndirectedGraph();

    /**
     * @param vex 是图中的某个顶点
     * @return 返回 vex位置
     */
    int locateVex(T vex) throws Exception;

    /**
     * @param vex 是图中的某个顶点
     * @return 返回第一个邻接顶点，如果没有返回空
     */
    T firstAdjVex(T vex) throws Exception;

    /**
     * @param vex 是图中的某个顶点
     * @param w   w是vex的邻接顶点
     * @return 返回相对于w的下一个邻接顶点，如果没有返回空
     */
    T nextAdjVex(T vex, T w) throws Exception;

    /**
     * 在图中添加新顶点
     *
     * @param vex 新顶点
     */
    void insertVex(T vex);

    /**
     * 在途中删除顶点
     *
     * @param vex 图中的顶点
     */
    void deleteVex(T vex) throws Exception;

    /**
     * 在图中增添弧<v,w>,如果是无向图，则还增添对称弧<w,v>
     *
     * @param v 图中的顶点
     * @param w 图中的另一个顶点
     */
    void insertArc(T v, T w, Comparable<?> weight) throws Exception;

    /**
     * 在图中删除弧<v,w>,如果是无向图，则还删除对称弧<w,v>
     *
     * @param v 图中的顶点
     * @param w 图中的另一个顶点
     */
    void deleteArc(T v, T w) throws Exception;

    /**
     * 对图进行深度优先遍历，在遍历过程中对每个顶顶访问一次
     */
    void dfsTraverse() throws Exception;

    /**
     * 对图进行广度优先遍历，在遍历过程中对每个顶点访问一次
     */
    void bfsTraveres();
}
