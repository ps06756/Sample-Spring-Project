package com.prepfortech.service;

import com.prepfortech.accessor.models.AuthDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    public AuthDTO findByToken(String token) {
        return null;
    }

    public boolean storeToken(String userId, String token) {
        return true;
    }
}
