package com.kuifir.jpms.problem.impl;

import com.kuifir.jpms.problem.crypto.Digest;
import com.kuifir.jpms.problem.crypto.DigestManager;
import com.kuifir.jpms.problem.crypto.Returned;

public final class DigestManagerImpl implements DigestManager {
    @Override
    public Returned<Digest> create(String algorithm) {
        return switch (algorithm) {
            case "SHA-256" -> Sha256.returnedSha256;
            case "SHA-512" -> Sha512.returnedSha512;
            default -> Returned.UNDEFINED;
        };
    }
}
