package com.prepfortech.accessor.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthDTO {
    private String authId;
    private String token;
    private String userId;
}
