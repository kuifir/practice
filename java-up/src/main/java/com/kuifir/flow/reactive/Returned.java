package com.kuifir.flow.reactive;

public sealed interface Returned<T> {
    record ReturnValue<T>(T returnValue) implements Returned {
    }

    record ErrorCode(Integer errorCode) implements Returned {
    }
}
