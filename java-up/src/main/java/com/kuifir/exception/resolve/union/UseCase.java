package com.kuifir.exception.resolve.union;

public class UseCase {
    public static void main(String[] args) {


        Returned<Digest> rt = Digest.of("SHA-128");
        switch (rt) {
            case Returned.ReturnValue rv -> {
                Digest d = (Digest) rv.returnValue();
                d.digest("Hello, world!".getBytes());
            }
            case Returned.ErrorCode ignored -> System.getLogger("com.kuifir.exception.resolve.union")
                    .log(System.Logger.Level.INFO,
                            "Failed to get instance of SHA-128");
        }
    }
}
