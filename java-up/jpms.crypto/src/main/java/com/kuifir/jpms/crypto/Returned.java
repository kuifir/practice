package com.kuifir.jpms.crypto;

public sealed interface Returned<T> {
    ErrorCode UNDEFINED = new ErrorCode(Integer.MAX_VALUE);

    record ReturnValue<T>(T returnValue) implements Returned {
    }

    record ErrorCode(Integer errorCode) implements Returned {
    }
}