package com.prepfortech.service;

import com.prepfortech.accessor.MovieAccessor;
import com.prepfortech.accessor.models.MovieDTO;
import com.prepfortech.service.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieService {

    @Autowired
    private MovieAccessor movieAccessor;

    public List<Movie> getMovies() {

        List<MovieDTO> movies = movieAccessor.getMovies();
        System.out.println("movies.size = " + movies.size());
        List<Movie> output = new ArrayList<>();
        for(MovieDTO movieDTO: movies) {
            Movie movie = new Movie();
            movie.setMovieName(movieDTO.getTitle());
            movie.setMovieTagLine(movieDTO.getTagline());
            output.add(movie);
        }
        return output;
    }
}
