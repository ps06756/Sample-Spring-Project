package com.prepfortech.mapper;

import com.prepfortech.accessor.S3Accessor;
import com.prepfortech.accessor.model.ShowDTO;
import com.prepfortech.controller.model.ShowOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowMapper {

    @Autowired
    private S3Accessor s3Accessor;

    public ShowOutput mapShowDtoToOutput(final ShowDTO input) {
        ShowOutput output = ShowOutput.builder()
                .showId(input.getShowId())
                .name(input.getName())
                .typeOfShow(input.getTypeOfShow())
                .genre(input.getGenre())
                .audience(input.getAudience())
                .rating(input.getRating())
                .length(input.getLength())
                .thumbnailLink(s3Accessor.getPreSignedUrl(input.getThumbnailPath(), 5*60))
                .build();
        return output;
    }
}
