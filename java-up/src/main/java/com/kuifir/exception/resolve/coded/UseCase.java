package com.kuifir.exception.resolve.coded;

public class UseCase {
    public static void main(String[] args) {
        Coded<Digest> coded = Digest.ofWithCoded("SHA-256");
        if (coded.errorCode() != 0) {
            // ignore
        } else {
            coded.returned().digest("Hello, world!".getBytes());
        }
    }
}
