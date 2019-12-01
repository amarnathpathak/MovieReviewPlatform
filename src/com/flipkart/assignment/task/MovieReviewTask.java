package com.flipkart.assignment.task;

import com.flipkart.assignment.enums.ReviewerType;
import com.flipkart.assignment.model.Movie;
import com.flipkart.assignment.model.User;
import com.flipkart.assignment.service.FlipKartMovieService;
import com.flipkart.assignment.service.FlipKartReviewService;
import com.flipkart.assignment.service.MovieService;
import com.flipkart.assignment.service.ReviewService;

/**
 * @author amarnath.pathak
 * @date 01/12/19
 **/
public class MovieReviewTask implements Runnable {

    private Movie movie;
    private User user;
    private int score;
    private ReviewService reviewService = FlipKartReviewService.getInstance();

    public MovieReviewTask(User u, Movie m, int s) {
        this.user = u;
        this.movie = m;
        this.score = s;
    }

    @Override
    public void run() {
        reviewService.reviewMovie(user, movie, score);
        reviewService.changeTypeIfRequired(user.getName());

    }
}
