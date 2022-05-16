package com.prepfortech.accessor.model;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Builder
@Getter
public class ProfileDTO {
    private String profileId;
    private String name;
    private ProfileType type;
    private Date createdAt;
    private String userId;
}
