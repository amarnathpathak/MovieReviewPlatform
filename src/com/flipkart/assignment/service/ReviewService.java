package com.flipkart.assignment.service;

import com.flipkart.assignment.enums.Genre;
import com.flipkart.assignment.enums.ReviewerType;
import com.flipkart.assignment.model.Movie;
import com.flipkart.assignment.model.User;

import java.util.List;

/**
 * @author amarnath.pathak
 * @date 01/12/19
 **/
public interface ReviewService {

    boolean addReview(String userName, String movieName, int score);
    void reviewMovie(User user, Movie movie, int score);
    void changeTypeIfRequired(String name);

    List<Movie> getTopMovieScoredByAndReleaseYear(int n, ReviewerType reviewerType, int releasedOn);

    List<Movie> getTopMovieScoredByAndGenre(int n, ReviewerType reviewerType, Genre genre);

    float averageSore(int releaseOn);

    float averageSore(Genre genre);

}
