package com.prepfortech.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WatchHistoryInput {
    private String profileId;
    private int watchTime;
}
