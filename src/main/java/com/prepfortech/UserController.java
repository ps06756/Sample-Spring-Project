package com.prepfortech;

import com.prepfortech.accessor.models.UserDTO;
import com.prepfortech.exception.UserNotFoundException;
import com.prepfortech.security.Roles;
import com.prepfortech.security.SecurityConstants;
import com.prepfortech.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@ControllerAdvice
public class UserController {

    @Autowired
    private UserService userService;

    @Secured({ Roles.Customer, Roles.User })
    @GetMapping("/passwordLink")
    public ResponseEntity<Boolean> sendResetPasswordLink(@RequestParam("email") String email) {
        try {
            System.out.println("Got a request for reset password link");
            userService.sendResetPasswordLink(email);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        catch(UserNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @PutMapping("/password")
    public boolean updatePassword(@RequestParam("userId") String userId, @RequestParam("password") String newPassword) {
        return userService.updatePassword(userId, newPassword);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO != null && userDTO.getPassword().equals(password)) {
            Date expirationDate = new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME);
            String token = Jwts.builder()
                    .setSubject(email)
                    .setAudience(userDTO.getRole().toString())
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
                    .compact();
            System.out.println("token = " + token);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!");
    }
}
