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
    void bfsTraveres() throws Exception;

    /**
     * 打印 v -w 的简单路径（无回路）
     * @param v 开始节点
     * @param w 结束节点
     * @throws Exception
     */
    void dfsPath(T v, T w) throws Exception;

    /**
     * v-m最短节点路径
     * @param v 开始节点
     * @param w 结束节点
     * @throws Exception
     */
    void bfsPath(T v, T w) throws Exception;

    /**
     * 打印连通图的关节点
     * 关节点的判断
     * 根节点：深度优先遍历是否只有一棵子树
     * 其他节点：子树节点是否有到祖先的回边
     */
    void printJointPoint() throws Exception;

    /**
     * 从某个源点到其余各顶点的最短路径
     * @param v 源顶点
     * @throws Exception 异常
     */
    void shortestPath_DIJ(T v) throws Exception;
    /**
     * 每一对顶点之间的最短路径
     * @throws Exception 异常
     */
    void shortestPath_Floyd() throws Exception;

    /**
     * 拓扑排序
     * @throws Exception
     */
    void topologicalSort() throws Exception;

    /**
     * 关键路径 弧为活动，顶点为事件
     * 1. 对顶点进行拓扑排序，求出每个时间的最早发生时间
     * 2.按逆拓扑序列求出每个事件的最迟发生时间
     * 3.求出每个活动的最早开始时间
     * 4.求出每个活动的最晚开始时间
     * 5，找出活动最早开始时间和最晚开始时间相同的活动，即为关键活动。
     * 关键路径可能不止一条。
     * @throws Exception
     */
    void criticalPath() throws Exception;

}
