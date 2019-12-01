package com.flipkart.assignment.service;

import com.flipkart.assignment.enums.Genre;
import com.flipkart.assignment.enums.ReviewerType;
import com.flipkart.assignment.model.Movie;

import java.util.List;
import java.util.Set;

public interface MovieService {

    boolean addMovie(String name, int releasingOn, Genre genre);
    Movie getMovie(String name);
    Set<Movie> getMovies(Genre genre);
    Set<Movie> getMovieByReleaseYear(int year);
    void updateScore(String name, ReviewerType r, int score);

}
