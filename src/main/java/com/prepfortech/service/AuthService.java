package com.prepfortech.service;

import com.prepfortech.accessor.models.AuthDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    public AuthDTO findByToken(String token) {
        return new AuthDTO(token, "5edf7c72-6a45-4d56-b73e-79f1d802");
    }

    public boolean storeToken(String userId, String token) {
        return true;
    }
}
