package com.prepfortech.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    private String message;

    public InvalidCredentialsException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
