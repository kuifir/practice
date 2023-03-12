package com.kuifir.common;

import java.io.Serializable;
import java.util.List;

/**
 * 树，可以有多个子树，也可以有多个文件
 */
public class Tree implements Serializable {
    private List<Tree> childTreeList;
    private List<Blob> blobList;
}
