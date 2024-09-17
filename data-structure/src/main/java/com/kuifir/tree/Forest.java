package com.kuifir.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 森林
 */
public class Forest<T> {

    private List<TreeNode<T>> roots;

    public ParentExpressTree<T> toParentExpress() {
        ParentExpressTree<T> tParentExpressTree = new ParentExpressTree<>();
        if (roots == null || roots.isEmpty()) {
            return tParentExpressTree;
        }
        List<ParentExpressTree.Node<T>> tree = new ArrayList<>();
        for (TreeNode<T> root : roots) {
            toParentExpress(tree, root, null);
        }
        tParentExpressTree.setTree(tree);
        return tParentExpressTree;
    }

    void toParentExpress(List<ParentExpressTree.Node<T>> tree, TreeNode<T> currentNode, ParentExpressTree.Node<T> parentNode) {
        if (currentNode != null) {
            ParentExpressTree.Node<T> node = new ParentExpressTree.Node<>(tree.size(), currentNode.getData(), parentNode == null ? null : parentNode.getId());
            tree.add(node);
            List<TreeNode<T>> child = currentNode.getChild();
            if (child != null && !child.isEmpty()) {
                for (TreeNode<T> tTreeNode : child) {
                    toParentExpress(tree, tTreeNode, node);
                }
            }
        }
    }

    public ChildBrotherExpressTree<T> toChildBrotherExpress() {
        if (roots == null || roots.isEmpty()) {
            return new ChildBrotherExpressTree<>();
        }
        ChildBrotherExpressTree.Node<T> childBrotherExpress = toChildBrotherExpress(roots.removeFirst(), roots);
        return new ChildBrotherExpressTree<>(childBrotherExpress);
    }

    ChildBrotherExpressTree.Node<T> toChildBrotherExpress(TreeNode<T> node, List<TreeNode<T>> brothers) {
        if (node == null) {
            return null;
        }
        ChildBrotherExpressTree.Node<T> currentNode = new ChildBrotherExpressTree.Node<>(node.getData());
        List<TreeNode<T>> child = node.getChild();
        if (child == null || child.isEmpty()) {
            currentNode.firstChild = null;
        } else {
            TreeNode<T> left = child.removeFirst();
            currentNode.firstChild = toChildBrotherExpress(left, child);
        }
        if (brothers == null || brothers.isEmpty()) {
            currentNode.brothers = null;
        } else {
            currentNode.brothers = toChildBrotherExpress(brothers.removeFirst(), brothers);
        }
        return currentNode;
    }



    public void print(){

    }

    public Forest(List<TreeNode<T>> roots) {
        this.roots = roots;
    }

    public List<TreeNode<T>> getRoots() {
        return roots;
    }

    public void setRoots(List<TreeNode<T>> roots) {
        this.roots = roots;
    }
}
