package com.flipkart.assignment.application;

import com.flipkart.assignment.enums.Genre;
import com.flipkart.assignment.enums.ReviewerType;
import com.flipkart.assignment.service.*;

/**
 * @author amarnath.pathak
 * @date 01/12/19
 **/
public class MovieReviewApplication {

    private static UserService userService = FlipKartUserService.getInstance();
    private static MovieService movieService = FlipKartMovieService.getInstance();
    private static ReviewService reviewService = FlipKartReviewService.getInstance();

    public static void main(String[] args) {
        movieService.addMovie("Don", 2006, Genre.ACTION);
        movieService.addMovie("Tiger", 2012, Genre.ACTION);
        movieService.addMovie("Padmavat", 2018, Genre.DRAMA);
        movieService.addMovie("Lunchbox-2", 2020, Genre.DRAMA);
        movieService.addMovie("Guru", 2006, Genre.DRAMA);
        movieService.addMovie("Metro", 2016, Genre.ROMANCE);
        movieService.addMovie("Metro", 2016, Genre.ROMANCE);
        movieService.addMovie("Krrish", 2006, Genre.ACTION);

        userService.addUser("SRK");
        userService.addUser("Salman");
        userService.addUser("Deepika");

        reviewService.addReview("SRK", "Don", 2);
        System.out.println();
        reviewService.addReview("SRK", "Padmavat", 8);
        System.out.println();
        reviewService.addReview("Salman", "Don", 5);
        System.out.println();
        reviewService.addReview("Deepika", "Don", 9);
        System.out.println();
        reviewService.addReview("Deepika", "Guru", 6);
        System.out.println();
        reviewService.addReview("SRK", "Don", 10);
        System.out.println();
        reviewService.addReview("Deepika", "Lunchbox-2", 5);
        System.out.println();
        reviewService.addReview("SRK", "Tiger", 5);
        System.out.println();
        reviewService.addReview("SRK", "Metro", 7);
        System.out.println();
        reviewService.addReview("Salman", "Krrish", 9);
        System.out.println();

        reviewService.getTopMovieScoredByAndReleaseYear(1, null, 2008);
        System.out.println();
        reviewService.getTopMovieScoredByAndReleaseYear(1, ReviewerType.CRITIC, 2006);
        System.out.println();
        reviewService.getTopMovieScoredByAndReleaseYear(2, ReviewerType.VIEWER, 2006);
        System.out.println();

        reviewService.getTopMovieScoredByAndGenre(1, null, Genre.DRAMA);
        System.out.println();
        reviewService.getTopMovieScoredByAndGenre(1, ReviewerType.VIEWER, Genre.DRAMA);
        System.out.println();
        reviewService.getTopMovieScoredByAndGenre(1, ReviewerType.CRITIC, Genre.ROMANCE);
        System.out.println();

        reviewService.averageSore(2006);
        System.out.println();

    }
}
