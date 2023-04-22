package com.kuifir.jpms.problem.crypto;

public sealed interface Returned<T> {
    Returned.ErrorCode UNDEFINED = new ErrorCode(Integer.MAX_VALUE);

    record ReturnValue<T>(T returnValue) implements Returned {
    }

    record ErrorCode(Integer errorCode) implements Returned {
    }
}