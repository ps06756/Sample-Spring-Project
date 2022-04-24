package com.prepfortech.accessor.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthDTO {
    public String token;
    public String userId;
}
