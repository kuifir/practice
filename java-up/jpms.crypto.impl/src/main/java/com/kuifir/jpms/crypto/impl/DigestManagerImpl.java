package com.kuifir.jpms.crypto.impl;

import com.kuifir.jpms.crypto.Digest;
import com.kuifir.jpms.crypto.DigestManager;
import com.kuifir.jpms.crypto.Returned;

public class DigestManagerImpl implements DigestManager {
    @Override
    public Returned<Digest> create(String algorithm) {
        return switch (algorithm) {
            case "SHA-256" -> Sha256.returnedSha256;
            case "SHA-512" -> Sha512.returnedSha512;
            default -> Returned.UNDEFINED;
        };
    }
}
