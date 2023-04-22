package com.kuifir.jpms.problem.crypto;

import com.kuifir.jpms.problem.impl.DigestManagerImpl;

import java.util.ServiceLoader;

public interface Digest {
    byte[] digest(byte[] message);

    static Returned of(String algorithm) {
        ServiceLoader<DigestManager> serviceLoader = ServiceLoader.load(DigestManager.class);
        //DigestManagerImpl 也暴露了出来，实际上应该通过暴露的DigestManager接口来访问，java9之前无法控制。
//        DigestManagerImpl digestManager = new DigestManagerImpl();
//        Returned rt = digestManager.create(algorithm);
//
//        switch (rt) {
//            case Returned.ReturnValue rv -> {
//                return rv;
//            }
//            case Returned.ErrorCode ignored -> {
//                return Returned.UNDEFINED;
//            }
//        }
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
