package com.prepfortech.controller.model;

import com.prepfortech.accessor.model.ShowAudience;
import com.prepfortech.accessor.model.ShowGenre;
import com.prepfortech.accessor.model.ShowType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShowOutput {
    private String showId;
    private String name;
    private ShowType typeOfShow;
    private ShowGenre genre;
    private ShowAudience audience;
    private double rating;
    private int length;
    private String thumbnailLink;
}
