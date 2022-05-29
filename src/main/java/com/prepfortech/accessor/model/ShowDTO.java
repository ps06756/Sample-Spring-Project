package com.prepfortech.accessor.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShowDTO {
    private String showId;
    private String name;
    private ShowType typeOfShow;
    private ShowGenre genre;
    private ShowAudience audience;
    private double rating;
    private int length;
    private String thumbnailPath;
}
