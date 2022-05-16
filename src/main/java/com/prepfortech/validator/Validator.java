package com.prepfortech.validator;

import com.prepfortech.accessor.ProfileAccessor;
import com.prepfortech.accessor.model.ProfileDTO;
import com.prepfortech.exceptions.InvalidProfileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Autowired
    private ProfileAccessor profileAccessor;

    public void validateProfile(final String profileId, final String userId) {
        ProfileDTO profileDTO = profileAccessor.getProfileByProfileId(profileId);
        if (profileDTO == null || !profileDTO.getUserId().equals(userId)) {
            throw new InvalidProfileException("Profile " + profileId + " is invalid or doesnt exist!");
        }
    }
}
