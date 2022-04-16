package com.prepfortech.accessor.models;

import lombok.Data;

@Data
public class ShowDTO {
    private String showId;
    private String showName;
    private ShowType showType;
    private ShowGenre genre;
    private PreferredAudience audience;
    private double averageRating;
    private int totalLength;
}
