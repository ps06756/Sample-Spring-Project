package com.prepfortech.exception;

public class VideoNotFoundException extends RuntimeException {
    private String videoId;

    public VideoNotFoundException(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String getMessage() {
        return "Video with " + videoId + " not found!";
    }
}
