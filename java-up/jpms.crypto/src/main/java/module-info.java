module jpms.crypto {
    requires jmh.core;
    requires org.junit.jupiter.api;
    exports com.kuifir.jpms.crypto;

    uses com.kuifir.jpms.crypto.DigestManager;
}