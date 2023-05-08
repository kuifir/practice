module jpms.crypto {
    requires jmh.core;
    requires org.junit.jupiter.api;
    requires java.logging;
    exports com.kuifir.jpms.crypto;

    uses com.kuifir.jpms.crypto.DigestManager;
}