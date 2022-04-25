package com.prepfortech;

import com.prepfortech.exception.DependencyFailureException;
import com.prepfortech.exception.VideoNotFoundException;
import com.prepfortech.security.Roles;
import com.prepfortech.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class VideoController {

    @Autowired
    VideoService videoService;

    @Secured({ Roles.Customer })
    @PutMapping("/video/{videoId}")
    public ResponseEntity updateRating(@PathVariable String videoId, @RequestParam("rating") double rating) {
        try {
            videoService.updateRating(videoId, rating);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        catch(VideoNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        catch(DependencyFailureException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getCause().getMessage());
        }
    }
}
