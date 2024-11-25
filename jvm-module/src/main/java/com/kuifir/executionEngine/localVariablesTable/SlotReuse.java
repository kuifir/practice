package com.kuifir.executionEngine.localVariablesTable;

/**
 * VM args: -verbose:gc
 */
public class SlotReuse {
    public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
//            placeholder = null;
        }
        // 变量会复用placeholder的槽，
        // 如果槽还存在有关于placeholder的引用，虽然已经离开了作用域，这时候不会回收内存
        int a = 0;
        System.gc();
    }
}
