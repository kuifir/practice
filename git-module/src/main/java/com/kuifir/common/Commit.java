package com.kuifir.common;

import java.io.Serializable;

public class Commit implements Serializable {
    /**
     * 一个Commit 指向一个树，包含一些提交的信息
     */
    private Tree tree;
    private Commit patent;
    private String author;
    private String committer;
}
