package com.prepfortech.accessor.models;

import lombok.Data;

@Data
public class UserDTO {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String phoneNo;
    private UserState userState;
}
