package com.prepfortech.service.models;

public class Movie {
    private String movieName;
    private String movieTagLine;

    public Movie() {
    }

    public Movie(String movieName, String movieTagLine) {
        this.movieName = movieName;
        this.movieTagLine = movieTagLine;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieTagLine() {
        return movieTagLine;
    }

    public void setMovieTagLine(String movieTagLine) {
        this.movieTagLine = movieTagLine;
    }
}
