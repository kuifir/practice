package com.kuifir.jpms.crypto;

public interface DigestManager {
    Returned<Digest> create(String algorithm);
}
