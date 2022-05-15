package com.prepfortech.service;

import com.prepfortech.accessor.AuthAccessor;
import com.prepfortech.accessor.UserAccessor;
import com.prepfortech.accessor.model.UserDTO;
import com.prepfortech.exceptions.DependencyFailureException;
import com.prepfortech.exceptions.InvalidCredentialsException;
import com.prepfortech.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthService {

    @Autowired
    private UserAccessor userAccessor;

    @Autowired
    private AuthAccessor authAccessor;

    /**
     *
     * @param email: Email of the user who wants to login
     * @param password: Password of the user who wants to login
     * @return : Jwt Token if email and password combination is correct
     */
    public String login(final String email, final String password) {
        UserDTO userDTO = userAccessor.getUserByEmail(email);
        if (userDTO != null && userDTO.getPassword().equals(password)) {
            String token = Jwts.builder()
                    .setSubject(email)
                    .setAudience(userDTO.getRole().name())
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY)
                    .compact();
            authAccessor.storeToken(userDTO.getUserId(), token);
            return token;
        }
        throw new InvalidCredentialsException("Either the email or password is incorrect!");
    }
}
