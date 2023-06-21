package com.kuifir.jpms.problem.crypto;

public class UseCase {
    public static void main(String[] args) {
        Returned<Digest> rt = Digest.of("SHA-256");
        switch (rt) {
            case Returned.ReturnValue rv -> {
                Digest d = (Digest) rv.returnValue();
                System.out.println(d.digest("Hello, world!".getBytes()));
            }
            case Returned.ErrorCode ec -> System.getLogger("co.ivi.jus.stack.union")
                    .log(System.Logger.Level.INFO,
                            "Failed to get instance of SHA-256");
        }
    }
}
