package com.prepfortech;

import com.prepfortech.service.MovieService;
import com.prepfortech.service.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    private List<Movie> getListOfMovies() {

        return movieService.getMovies();
    }
}
