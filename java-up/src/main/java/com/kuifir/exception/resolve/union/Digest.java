package com.kuifir.exception.resolve.union;

public sealed abstract class Digest {


    public static Returned<Digest> of(String algorithm) {
        return switch (algorithm) {
            case "SHA-256" -> new Returned.ReturnValue(new SHA256());
            case "SHA-512" -> new Returned.ReturnValue(new SHA512());
            case null, default -> new Returned.ErrorCode(-1);
        };
    }

    abstract byte[] digest(byte[] message);

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
