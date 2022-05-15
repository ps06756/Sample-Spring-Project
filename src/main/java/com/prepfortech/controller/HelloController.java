package com.prepfortech.controller;

import com.prepfortech.security.Roles;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("/")
    @Secured({ Roles.Customer, Roles.User })
    public String sayHello() {
        return "Hello world In netflix clone!";
    }
}
