package com.kuifir.graph.impl;

import com.kuifir.graph.Graph;

import java.util.Arrays;
import java.util.Stack;

/**
 * 图的邻接表存储结构
 *
 * @param <T> 顶点信息类型
 *            边的信息类型 为Integer
 */
public class AdjacencyListGraphIntegerWeight<T> extends AdjacencyListGraph<T, Integer> implements Graph<T> {


    public AdjacencyListGraphIntegerWeight(boolean unDirectedGraphFlag, int maxVexNum) {
        super(unDirectedGraphFlag, maxVexNum);
    }


    @Override
    public void criticalPath() throws Exception {
        if (isUndirectedGraph()) {
            throw new UnsupportedOperationException();
        }
        Stack<Integer> beginVexs = new Stack<>();
        Stack<Integer> endVexs = new Stack<>();
        // 节点入度
        int[] inDegrees = new int[vexNum];
        // 各事件最早开始时间
        int[] eventEarliestBeginTime = new int[vexNum];
        // 各事件最迟开始时间
        int[] eventLastBeginTime = new int[vexNum];

        // 初始化入度
        for (int i = 0; i < vexNum; i++) {
            ArcNode<Integer> arcNode = vertices[i].firstArc;
            for (; arcNode != null; arcNode = arcNode.nextArc) {
                inDegrees[arcNode.adjacencyVex]++;
            }
        }

        int count = 0;
        // 依次找到入度为0的顶点入栈，该顶点出度的顶点，入度减一
        for (int i = 0; i < inDegrees.length; i++) {
            if (inDegrees[i] == 0) {
                beginVexs.push(i);
            }
        }
        while (!beginVexs.empty()) {
            Integer pop = beginVexs.pop();
            endVexs.push(pop);
            count++;
            ArcNode<Integer> arcNode = vertices[pop].firstArc;
            for (; arcNode != null; arcNode = arcNode.nextArc) {
                inDegrees[arcNode.adjacencyVex]--;
                // 更新事件最早开始时间
                if (eventEarliestBeginTime[arcNode.adjacencyVex] < eventEarliestBeginTime[pop] + arcNode.info) {
                    eventEarliestBeginTime[arcNode.adjacencyVex] = eventEarliestBeginTime[pop] + arcNode.info;
                }
                if (inDegrees[arcNode.adjacencyVex] == 0) {
                    beginVexs.push(arcNode.adjacencyVex);
                }
            }
        }
        if (count < vexNum) {
            throw new UnsupportedOperationException("有向图中回路");
        }
        // 逆序获取每个事件的最晚开始时间
        Integer peek = endVexs.peek();
        Arrays.fill(eventLastBeginTime, eventEarliestBeginTime[peek]);
        while (!endVexs.empty()) {
            Integer pop = endVexs.pop();
            beginVexs.push(pop);
            ArcNode<Integer> arcNode = vertices[pop].firstArc;
            for (; arcNode != null; arcNode = arcNode.nextArc) {
                if (eventLastBeginTime[pop] > eventLastBeginTime[arcNode.adjacencyVex] - arcNode.info) {
                    eventLastBeginTime[pop] = eventLastBeginTime[arcNode.adjacencyVex] - arcNode.info;
                }
            }
        }

        // 判断是否为关键活动
        System.out.println("关键路径：");
        while (!beginVexs.empty()) {
            Integer pop = beginVexs.pop();
            endVexs.push(pop);
            ArcNode<Integer> arcNode = vertices[pop].firstArc;
            // 活动最早开始时间
            int activityEarliest = eventEarliestBeginTime[pop];
            for (; arcNode != null; arcNode = arcNode.nextArc) {
                // 比较活动的最早最晚开始时间
                if (activityEarliest == eventLastBeginTime[arcNode.adjacencyVex] - arcNode.info) {
                    System.out.println("(" + vertices[pop].data + "," + vertices[arcNode.adjacencyVex].data + "),开始时间" + activityEarliest);
                }
            }
        }


    }
}