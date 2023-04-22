package com.kuifir.jpms.problem.impl;

import com.kuifir.jpms.problem.crypto.Digest;
import com.kuifir.jpms.problem.crypto.Returned;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class Sha256 implements Digest {
    static final Returned.ReturnValue<Digest> returnedSha256;

    private final MessageDigest md;

    static {
        Sha256 sha256 = new Sha256();
        returnedSha256 = sha256.md == null ?
                null : new Returned.ReturnValue<>(sha256);
    }

    private Sha256() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            md = null;
        }
        this.md = md;
    }

    @Override
    public byte[] digest(byte[] message) {
        return md.digest(message);
    }
}
