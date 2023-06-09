package com.kuifir.jpms.crypto.impl;

import com.kuifir.jpms.crypto.Digest;
import com.kuifir.jpms.crypto.Returned;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class Sha512 implements Digest {
    static final Returned.ReturnValue<Digest> returnedSha512;

    private final MessageDigest md;

    static {
        Sha512 Sha512 = new Sha512();
        returnedSha512 = Sha512.md == null ?
                null : new Returned.ReturnValue<>(Sha512);
    }

    private Sha512() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
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
