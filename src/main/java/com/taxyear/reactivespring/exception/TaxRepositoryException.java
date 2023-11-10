package com.taxyear.reactivespring.exception;

import lombok.Getter;

@Getter
public final class TaxRepositoryException extends RuntimeException {
    public TaxRepositoryException(Throwable cause) {
        super(cause);
    }

    public TaxRepositoryException() {
        this(null);
    }
}
