package com.prepfortech.accessor.models;

import lombok.Data;

import java.util.Date;

@Data
public class VideoDTO {
    private String videoId;
    private String name;
    private String seriesId;
    private String showId;
    private double rating;
    private Date releaseDate;
    private int totalLength;
}
