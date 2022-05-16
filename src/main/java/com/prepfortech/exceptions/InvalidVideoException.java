package com.prepfortech.exceptions;

public class InvalidVideoException extends RuntimeException {
    public InvalidVideoException(final String message) {
        super(message);
    }
}
