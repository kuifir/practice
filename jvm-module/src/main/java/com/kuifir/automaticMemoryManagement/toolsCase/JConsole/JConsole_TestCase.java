package com.kuifir.automaticMemoryManagement.toolsCase.JConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * Vm args: -Xms100m -Xmx100m -XX:+UseSerialGC
 */
public class JConsole_TestCase {
    static class OOMObject {
        public byte[] placeHolder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            // 稍作延时，令监视曲线的变化更明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
        System.gc();
        fillHeap(1000);
    }
}
