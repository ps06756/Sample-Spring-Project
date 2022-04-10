package com.prepfortech.service;

import com.prepfortech.config.AWSConfig;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import com.prepfortech.accessor.MovieAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;
import java.util.List;

@Component
public class MovieService {

    @Autowired
    private MovieAccessor movieAccessor;

    @Autowired
    private AwsCredentialsProvider awsCredentialsProvider;

    public List<String> getMovies() {
        return movieAccessor.getMovies();
    }

    public String getMovieURL(String movieName) {
        String key = "movies/WolfOfWallStreet.mp4";
        String bucketName = "pft-netflix-demo";

        S3Presigner presigner = S3Presigner
                .builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(awsCredentialsProvider)
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .key(key)
                .bucket(bucketName)
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest
                .builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest request = presigner.presignGetObject(getObjectPresignRequest);

        return request.url().toString();
    }
}
