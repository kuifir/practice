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

    List<Node<T>> getTree() {
        return tree;
    }

    void setTree(List<Node<T>> tree) {
        this.tree = tree;
    }

    public ChildBrotherExpressTree<T> toChildBrotherExpress() {
        List<Node<T>> roots = getRootNodes();
        Map<Integer, List<Node<T>>> parentIdChildMap = getParentIdChildMap();
        ChildBrotherExpressTree.Node<T> childBrotherExpress = toChildBrotherExpress(roots, parentIdChildMap);
        return new ChildBrotherExpressTree<>(childBrotherExpress);
    }

    ChildBrotherExpressTree.Node<T> toChildBrotherExpress(List<Node<T>> nodes, Map<Integer, List<Node<T>>> parentIdChildMap) {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        Node<T> node = nodes.removeFirst();
        ChildBrotherExpressTree.Node<T> currentNode = new ChildBrotherExpressTree.Node<>(node.getData());
        List<Node<T>> currentChild = parentIdChildMap.get(node.getId());
        if (currentChild != null && !currentChild.isEmpty()) {
            currentNode.firstChild = toChildBrotherExpress(currentChild, parentIdChildMap);
        }
        if(!nodes.isEmpty()){
            currentNode.brothers = toChildBrotherExpress(nodes,parentIdChildMap);
        }
        return currentNode;
    }

    public MultiwayTree<T> toMultiWayTree() {
        List<Node<T>> roots = getRootNodes();
        Map<Integer, List<Node<T>>> parentIdChildMap = getParentIdChildMap();
        List<TreeNode<T>> treeNodes = fillMultiwayTree(roots, parentIdChildMap);
        if (treeNodes == null || treeNodes.isEmpty()) {
            return new MultiwayTree<>(null);
        }
        return new MultiwayTree<>(treeNodes.getFirst());
    }

    public Forest<T> toForest() {
        List<Node<T>> roots = getRootNodes();
        Map<Integer, List<Node<T>>> parentIdChildMap = getParentIdChildMap();
        List<TreeNode<T>> treeNodes = fillMultiwayTree(roots, parentIdChildMap);
        if (treeNodes == null || treeNodes.isEmpty()) {
            return new Forest<>(null);
        }
        return new Forest<>(treeNodes);
    }

    List<Node<T>> getRootNodes() {
        return tree.stream().filter(e -> Objects.isNull(e.getParentId())).collect(Collectors.toList());
    }

    Map<Integer, List<Node<T>>> getParentIdChildMap() {
        return tree.stream().filter(e -> Objects.nonNull(e.getParentId())).collect(Collectors.groupingBy(Node::getParentId));
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
