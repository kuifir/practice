package com.kuifir.exception.resolve.union;

public class UseCase {
    public static void main(String[] args) {

        Returned<Digest> rt = Digest.of("SHA-256");
        switch (rt) {
            case Returned.ReturnValue rv -> {
                Digest d = (Digest) rv.returnValue();
                d.digest("Hello, world!".getBytes());
            }
            case Returned.ErrorCode ignored -> System.out.println("Failed to get instance of SHA-256");
        }
    }
}
