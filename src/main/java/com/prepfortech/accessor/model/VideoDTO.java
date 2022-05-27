package com.prepfortech.accessor.model;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Builder
@Getter
public class VideoDTO {
    private String videoId;
    private String name;
    private String seriesId;
    private String showId;
    private double rating;
    private Date releaseDate;
    private int totalLength;
    private String videoPath;
}
