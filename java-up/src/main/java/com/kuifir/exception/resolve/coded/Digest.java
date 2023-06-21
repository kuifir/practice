package com.kuifir.exception.resolve.coded;

import java.security.NoSuchAlgorithmException;

public sealed abstract class Digest permits Digest.SHA256, Digest.SHA512 {
    private static Digest.SHA256 sha256 = new Digest.SHA256();
    private static Digest.SHA512 sha512 = new Digest.SHA512();

    abstract byte[] digest(byte[] message);

    /**
     * 使用这个 of 方法的代码，就需要处理这个检查型异常。
     *
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Digest of(String algorithm) throws NoSuchAlgorithmException {
        return switch (algorithm) {
            case "SHA-256" -> new SHA256();
            case "SHA-512" -> new SHA512();
            default -> throw new NoSuchAlgorithmException();
        };
    }

    public static Coded<Digest> ofWithCoded(String algorithm) {
        return switch (algorithm) {
            case "SHA-256" -> new Coded(sha256, 0);
            case "SHA-512" -> new Coded(sha512, 0);
            default -> new Coded(null, -1);
        };
    }


    public static final class SHA256 extends Digest {

        @Override
        byte[] digest(byte[] message) {
            return new byte[0];
        }
    }

    public static final class SHA512 extends Digest {

        @Override
        byte[] digest(byte[] message) {
            return new byte[0];
        }
    }
}
