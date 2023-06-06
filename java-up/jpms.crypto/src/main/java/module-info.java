module jpms.crypto {
   /**
    * 运行子模块时候需要注释掉
    */
    requires org.junit.jupiter.api;

    requires jmh.core;
    requires java.logging;
    requires cn.hutool;
    exports com.kuifir.jpms.crypto;

    uses com.kuifir.jpms.crypto.DigestManager;
}