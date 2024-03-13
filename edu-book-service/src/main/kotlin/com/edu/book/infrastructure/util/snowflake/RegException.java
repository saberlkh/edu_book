package com.edu.book.infrastructure.util.snowflake;

public final class RegException extends RuntimeException {
    public RegException(final String errorMessage, final Exception cause) {
        super(errorMessage, cause);
    }

    public RegException(final String errorMessage) {
        super(errorMessage);
    }
}
