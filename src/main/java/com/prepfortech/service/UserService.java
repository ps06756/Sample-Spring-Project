package com.prepfortech.service;

import com.prepfortech.accessor.EmailAccessor;
import com.prepfortech.accessor.OtpAccessor;
import com.prepfortech.accessor.UserAccessor;
import com.prepfortech.accessor.model.*;
import com.prepfortech.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserService {

    @Autowired
    private UserAccessor userAccessor;

    @Autowired
    private OtpAccessor otpAccessor;

    @Autowired
    private EmailAccessor emailAccessor;

    public void addNewUser(final String email, final String name, final String password, final String phoneNo) {
        if (phoneNo.length() != 10) {
            throw new InvalidDataException("Phone no " + phoneNo + " is invalid!");
        }
        if (password.length() < 6) {
            throw new InvalidDataException("Password is too simple!");
        }
        if (name.length() < 5) {
            throw new InvalidDataException("Name is not correct!");
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (!pat.matcher(email).matches()) {
            throw new InvalidDataException("Email is not correct!");
        }

        UserDTO userDTO = userAccessor.getUserByEmail(email);
        if (userDTO != null) {
            throw new InvalidDataException("User with given email already exists!");
        }
        userDTO = userAccessor.getUserByPhoneNo(phoneNo);
        if (userDTO != null) {
            throw new InvalidDataException("User with given phoneNo already exists!");
        }

        userAccessor.addNewUser(email, name, password, phoneNo, UserState.ACTIVE, UserRole.ROLE_USER);
    }

    public void activateSubscription() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        userAccessor.updateUserRole(userDTO.getUserId(), UserRole.ROLE_CUSTOMER);
    }

    public void deleteSubscription() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        userAccessor.updateUserRole(userDTO.getUserId(), UserRole.ROLE_USER);
    }

    public void verifyEmail(final String otp) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        if (userDTO.getEmailVerificationStatus().equals(EmailVerificationStatus.UNVERIFIED)) {
            OtpDTO otpDTO = otpAccessor.getUnsedOtp(userDTO.getUserId(), otp, OtpSentTo.EMAIL);
            if (otpDTO != null) {
                userAccessor.updateEmailVerificationStatus(userDTO.getUserId(), EmailVerificationStatus.VERIFIED);
                otpAccessor.updateOtpState(otpDTO.getOtpId(), OtpState.USED);
            }
            else {
                throw new InvalidDataException("Otp does not exist!");
            }
        }
    }
    public void sendEmailOtp() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        String otp = generateOtp();

        otpAccessor.addNewOtp(userDTO.getUserId(), otp, OtpSentTo.EMAIL);
        emailAccessor.sendEmail(userDTO.getName(), userDTO.getEmail(), "OTP for email verification",
                "Your OTP for verifying the email is " + otp);
    }

    public void verifyPhone(final String otp) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        if (userDTO.getPhoneVerificationStatus().equals(PhoneVerificationStatus.UNVERIFIED)) {
            OtpDTO otpDTO = otpAccessor.getUnsedOtp(userDTO.getUserId(), otp, OtpSentTo.PHONE);
            if (otpDTO != null) {
                userAccessor.updatePhoneVerificationStatus(userDTO.getUserId(), PhoneVerificationStatus.VERIFIED);
                otpAccessor.updateOtpState(otpDTO.getOtpId(), OtpState.USED);
            }
            else {
                throw new InvalidDataException("Otp does not exist!");
            }
        }
    }

    private String generateOtp() {
        int min = 100000;
        int max = 999999;

        int otp = (int)(Math.random()*(max - min + 1) + min);
        return Integer.toString(otp);
    }
}
