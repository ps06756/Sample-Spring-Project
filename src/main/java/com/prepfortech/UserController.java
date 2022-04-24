package com.prepfortech;

import com.prepfortech.exception.UserNotFoundException;
import com.prepfortech.security.Roles;
import com.prepfortech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Secured({ Roles.Customer })
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
}
