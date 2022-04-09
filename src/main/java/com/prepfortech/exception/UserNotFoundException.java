package com.prepfortech.exception;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException {
    private String email;
    public UserNotFoundException(String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return "User not found with the email " + email;
    }
}
