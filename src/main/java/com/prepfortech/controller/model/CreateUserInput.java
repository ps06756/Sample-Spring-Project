package com.prepfortech.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserInput {
    private String name;
    private String email;
    private String phoneNo;
    private String password;
}
