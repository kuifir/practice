package com.kuifir.automaticMemoryManagement.toolsCase;

/**
 * JHSDB本身对压缩指针的支持存在很多缺陷，建议用64位系统在实验时禁用压缩指针
 * 另外为了后续操作时可以加快在内存中搜索对象的速度，建议限制下java堆的大小
 * <p></p>
 * VM args: -Xmx10m -XX:+UseSerialGC -XXL-UseCompressedOops
 */
public class JHSDB_TestCase {
    static class Test{
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();
        void foo(){
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done"); // 这里设置一个断点
        }
    }
    private static class ObjectHolder{

    }

    public static void main(String[] args) {
        Test test = new Test();
        test.foo();
    }
}
