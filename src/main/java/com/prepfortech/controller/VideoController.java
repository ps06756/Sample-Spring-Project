package com.prepfortech.controller;

import com.prepfortech.exceptions.InvalidVideoException;
import com.prepfortech.security.Roles;
import com.prepfortech.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

    @Autowired
    VideoService videoService;

    @GetMapping("/video/{videoId}/link")
    @Secured({ Roles.Customer })
    public ResponseEntity<String> getVideoLink(@PathVariable("videoId") String videoId) {
        try {
            String videoUrl = videoService.getVideoUrl(videoId);
            return ResponseEntity.status(HttpStatus.OK).body(videoUrl);
        }
        catch(InvalidVideoException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/video/{videoId}/thumbnail")
    @Secured({ Roles.Customer })
    public ResponseEntity<String> getVideoThumbnail(@PathVariable("videoId") String videoId) {
        try {
            String videoUrl = videoService.getVideoThumbnail(videoId);
            return ResponseEntity.status(HttpStatus.OK).body(videoUrl);
        }
        catch(InvalidVideoException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
