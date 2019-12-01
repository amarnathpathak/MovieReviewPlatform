package com.flipkart.assignment.service;

import com.flipkart.assignment.enums.Genre;
import com.flipkart.assignment.enums.ReviewerType;
import com.flipkart.assignment.model.Movie;

import java.util.*;
import java.util.stream.Collectors;

import static com.sun.tools.javac.util.StringUtils.toLowerCase;

/**
 * @author amarnath.pathak
 * @date 01/12/19
 **/
public class FlipKartMovieService implements MovieService {
    private final Map<String, Movie> movies = new HashMap<>();

    private static MovieService instance;

    private FlipKartMovieService() {
    }

    public static MovieService getInstance() {
        if (instance == null) {

            synchronized (FlipKartMovieService.class) {
                if (instance == null) {
                    instance = new FlipKartMovieService();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean addMovie(String name, int releasingOn, Genre genre) {
        if (movies.containsKey(toLowerCase(name))) {
            System.out.println("Movie Already Registered");
            return false;
        }
        movies.put(toLowerCase(name), new Movie(name, releasingOn, genre));
        System.out.println("Movie Added : " + name + " releasing on " + releasingOn + " genre " + genre);
        return true;
    }


    @Override
    public Movie getMovie(String name) {
        if (!movies.containsKey(toLowerCase(name))) {
            System.out.println("No Movie Registered with name: " + name);
            return null;
        }
        return movies.get(toLowerCase(name));
    }

    @Override
    public Set<Movie> getMovies(Genre genre) {
        return movies.values().stream().filter(m -> m.getGenre().equals(genre)).collect(Collectors.toSet());
    }

    @Override
    public Set<Movie> getMovieByReleaseYear(int releasingOn) {
        return movies.values().stream().filter(m -> m.getReleaseOn().equals(releasingOn)).collect(Collectors.toSet());
    }


    @Override
    public void updateScore(String name, ReviewerType r, int score) {
        Movie movie = getMovie(name);
        switch (r) {
            case VIEWER: movie.incUserScore(score);
            break;
            case CRITIC: movie.incCriticScore(score);
        }
        movie.incReviewCount();
    }


}
