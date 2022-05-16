package com.prepfortech.exceptions;

public class InvalidProfileException extends RuntimeException {
    public InvalidProfileException(final String message) {
        super(message);
    }
}
