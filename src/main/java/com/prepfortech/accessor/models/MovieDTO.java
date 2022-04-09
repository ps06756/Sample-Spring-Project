package com.prepfortech.accessor.models;

public class MovieDTO {
    private String title;
    private String tagline;
    private double budget;

    public MovieDTO(String title, String tagline) {
        this.title = title;
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}
