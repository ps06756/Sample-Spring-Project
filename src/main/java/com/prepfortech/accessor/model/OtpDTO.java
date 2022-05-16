package com.prepfortech.accessor.model;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Builder
@Getter
public class OtpDTO {
    private String otpId;
    private String userId;
    private String otp;
    private OtpState state;
    private Date createdAt;
    private OtpSentTo sentTo;
}
