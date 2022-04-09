package com.prepfortech;

import com.prepfortech.exception.UserNotFoundException;
import com.prepfortech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

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
}