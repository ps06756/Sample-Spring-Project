package com.prepfortech.service;

import com.prepfortech.accessor.UserAccessor;
import com.prepfortech.accessor.models.UserDTO;
import com.prepfortech.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserAccessor userAccessor;

    public void sendResetPasswordLink(String email) {
        UserDTO userDTO = userAccessor.getUserByEmail(email);

        if (userDTO == null) {
            throw new UserNotFoundException(email);
        }

        // Write the code for generating link
        // Write the code for sending the email
    }
}
