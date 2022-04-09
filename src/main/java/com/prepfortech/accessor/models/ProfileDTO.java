package com.prepfortech.accessor.models;

import lombok.Data;

import java.util.Date;

@Data
public class ProfileDTO {
    private String profileId;
    private String  name;
    private ProfileType type;
    private Date timestamp;
    private String userId;
}
