package com.kuifir.common.refs.heads;

import com.kuifir.common.Commit;

import java.io.Serializable;

/**
 * 代表一个分支  指向 一次提交
 *  git cat-file -t master
 * commit
 *  git cat-file -p master
 * tree 6d106e3bc2d25c649b339f08e5526b648bd946a1
 * parent 7c22c0163ad33d61c95ef772a8fe47d6cd645113
 * author kuifir <kuifir@163.com> 1678612526 +0800
 * committer kuifir <kuifir@163.com> 1678612526 +0800
 */
public class Head implements Serializable {
    private Commit commit;

}
