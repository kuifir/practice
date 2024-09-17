package com.kuifir.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 树/森林的双亲表示法表示树
 */
public class ParentExpressTree<T> {
    private List<Node<T>> tree;

    public List<Node<T>> getTree() {
        return tree;
    }

    public void setTree(List<Node<T>> tree) {
        this.tree = tree;
    }

    public MultiwayTree<T> toMultiWayTree() {
        List<Node<T>> roots = tree.stream().filter(e -> Objects.isNull(e.getParentId())).toList();
        Map<Integer, List<Node<T>>> parentIdChildMap = tree.stream().filter(e -> Objects.nonNull(e.getParentId())).collect(Collectors.groupingBy(Node::getParentId));
        List<TreeNode<T>> treeNodes = fillMultiwayTree(roots, parentIdChildMap);
        if (treeNodes == null || treeNodes.isEmpty()) {
            return new MultiwayTree<>(null);
        }
        return new MultiwayTree<>(treeNodes.getFirst());
    }

    public Forest<T> toForest() {
        List<Node<T>> roots = tree.stream().filter(e -> Objects.isNull(e.getParentId())).toList();
        Map<Integer, List<Node<T>>> parentIdChildMap = tree.stream().filter(e -> Objects.nonNull(e.getParentId())).collect(Collectors.groupingBy(Node::getParentId));
        List<TreeNode<T>> treeNodes = fillMultiwayTree(roots, parentIdChildMap);
        if (treeNodes == null || treeNodes.isEmpty()) {
            return new Forest<>(null);
        }
        return new Forest<>(treeNodes);
    }

    List<TreeNode<T>> fillMultiwayTree(List<Node<T>> roots, Map<Integer, List<Node<T>>> parentIdChildRelation) {
        List<TreeNode<T>> treeNodes = new ArrayList<>();
        for (Node<T> root : roots) {
            TreeNode<T> treeNode = new TreeNode<>();
            treeNode.setData(root.getData());
            List<Node<T>> child = parentIdChildRelation.get(root.getId());
            if (child != null && !child.isEmpty()) {
                List<TreeNode<T>> childNodes = fillMultiwayTree(child, parentIdChildRelation);
                treeNode.setChild(childNodes);
            }
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    static class Node<T> {
        private Integer id;
        private T data;
        private Integer parentId;

        public Node() {

        }

        public Node(Integer id, T data, Integer parentId) {
            this.id = id;
            this.data = data;
            this.parentId = parentId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }
    }
}
