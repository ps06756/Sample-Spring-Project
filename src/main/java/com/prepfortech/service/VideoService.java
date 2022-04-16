package com.prepfortech.service;

import com.prepfortech.accessor.VideoAccessor;
import com.prepfortech.accessor.models.VideoDTO;
import com.prepfortech.exception.VideoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoService {

    @Autowired
    private VideoAccessor videoAccessor;

    public boolean updateRating(String videoId, double newRating) {
        VideoDTO videoDTO = videoAccessor.findById(videoId);

        if (videoDTO == null) {
            throw new VideoNotFoundException(videoId);
        }
        else {
            return videoAccessor.updateVideoRating(videoId, newRating);
        }
    }
}
