package com.prepfortech.accessor;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class MovieAccessor {
    public List<String> getMovies() {
        return Arrays.asList("Dark night rises", "Batman begins");
    }
}
