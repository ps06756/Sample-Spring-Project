package com.prepfortech;

import com.prepfortech.security.Roles;
import com.prepfortech.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;


    @Secured({ Roles.Customer })
    @GetMapping("/movies")
    private List<String> getListOfMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/movieUrl")
    private String getMovieUrl() {
        return movieService.getMovieURL("WolfOfWallStreet");
    }
}
