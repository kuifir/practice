package com.kuifir.jpms.problem.crypto;

public interface DigestManager {
    Returned<Digest> create(String algorithm);
}
