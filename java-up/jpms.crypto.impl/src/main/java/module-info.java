
module jpms.crypto.impl {
    requires jpms.crypto;

    provides com.kuifir.jpms.crypto.DigestManager with com.kuifir.jpms.crypto.impl.DigestManagerImpl;
}