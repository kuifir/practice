package com.kuifir.jpms.crypto;


import java.util.ServiceLoader;

public interface Digest {
    byte[] digest(byte[] message);

    static Returned of(String algorithm) {
        ServiceLoader<DigestManager> serviceLoader = ServiceLoader.load(DigestManager.class);
        for (DigestManager cryptoManager : serviceLoader) {
            Returned rt = cryptoManager.create(algorithm);
            switch (rt) {
                case Returned.ReturnValue rv -> {
                    return rv;
                }
                case Returned.ErrorCode ignored -> {
                    continue;
                }
            }
        }
        return Returned.UNDEFINED;
    }
}
