package com.prepfortech.accessor.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDTO {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String phoneNo;
    private UserState state;
    private UserRole role;
}
