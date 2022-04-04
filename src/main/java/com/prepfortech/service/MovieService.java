package com.prepfortech.service;

import com.prepfortech.accessor.MovieAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieService {

    @Autowired
    private MovieAccessor movieAccessor;

    public List<String> getMovies() {
        return movieAccessor.getMovies();
    }
}
