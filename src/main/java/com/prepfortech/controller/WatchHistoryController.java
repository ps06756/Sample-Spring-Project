package com.prepfortech.controller;

import com.prepfortech.controller.model.WatchHistoryInput;
import com.prepfortech.exceptions.InvalidProfileException;
import com.prepfortech.exceptions.InvalidVideoException;
import com.prepfortech.security.Roles;
import com.prepfortech.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WatchHistoryController {

    @Autowired
    WatchHistoryService watchHistoryService;

    @PostMapping("/video/{videoId}/watchTime")
    @Secured({Roles.Customer })
    public ResponseEntity<Void> updateWatchHistory(@PathVariable("videoId") String videoId,
                                                   @RequestBody WatchHistoryInput watchHistoryInput) {
        try {
            String profileId = watchHistoryInput.getProfileId();
            int watchTime = watchHistoryInput.getWatchTime();
            watchHistoryService.updateWatchHistory(profileId, videoId, watchTime);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(InvalidVideoException | InvalidProfileException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
