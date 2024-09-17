package com.kuifir.tree;

import java.util.List;

public class TreeNode<T> {
    private T data;
    private List<TreeNode<T>> child;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<TreeNode<T>> getChild() {
        return child;
    }

    public void setChild(List<TreeNode<T>> child) {
        this.child = child;
    }
}
