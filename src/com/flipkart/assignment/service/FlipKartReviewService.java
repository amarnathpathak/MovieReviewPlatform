package com.flipkart.assignment.service;

import com.flipkart.assignment.enums.Genre;
import com.flipkart.assignment.enums.ReviewerType;
import com.flipkart.assignment.model.Movie;
import com.flipkart.assignment.model.User;
import com.flipkart.assignment.task.MovieReviewTask;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author amarnath.pathak
 * @date 01/12/19
 **/
public class FlipKartReviewService implements ReviewService {

    private static ReviewService instance;
    private UserService userService = FlipKartUserService.getInstance();
    private MovieService movieService = FlipKartMovieService.getInstance();
    private Map<String, Set<Movie>> userMovieReviewed = new HashMap<>();
    private final Map<ReviewerType, List<Movie>> reviewerMovies = new HashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool(50);

    private FlipKartReviewService() {
    }

    public static ReviewService getInstance() {
        if (instance == null) {

            synchronized (FlipKartReviewService.class) {
                if (instance == null) {
                    instance = new FlipKartReviewService();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean addReview(String userName, String movieName, int score) {
        if (!isValidScore(score)) {
            System.out.println("Invalid Score " + score);
            return false;
        }
        User user = userService.getUser(userName);
        Movie movie = movieService.getMovie(movieName);
        if (user == null || movie == null) {
            System.out.println("User or Movie not exist");
            return false;
        }
        if (!movie.hasReleased()) {
            System.out.println(movieName + " not yet released");
            return false;
        }
        if (hasReviewed(user, movie)) {
            System.out.println("Hey " + userName + "! You already reviewed "+ movieName +  ", multiple review is not allowed");
            return false;
        }
        reviewMovie(user, movie, score);
        changeTypeIfRequired(user.getName());
        //executor.submit(new MovieReviewTask(user, movie, score));
        return true;

    }

    @Override
    public List<Movie> getTopMovieScoredByAndReleaseYear(int n, ReviewerType reviewerType, int releasedOn) {
        System.out.println("getTopMovieScoredByAndReleaseYear: " + n + " " + reviewerType + " " + releasedOn);
        Set<Movie> movies = getMovieBy(reviewerType, null, releasedOn);
        if (movies == null || movies.size() <= 0) {
            System.out.println("No movie by Reviewer");
            return null;
        }
        List<Movie> movieByScore = new ArrayList<>(movies);
        if (movieByScore != null && movieByScore.size() > 0) {
            List<Movie> m = sortAndGetTop(n, reviewerType, movieByScore);
            System.out.println(m);
            return m;
        }
        System.out.println("No Movie review on :" + releasedOn);
        System.out.println();
        return null;
    }

    @Override
    public List<Movie> getTopMovieScoredByAndGenre(int n, ReviewerType reviewerType, Genre genre) {
        System.out.println("getTopMovieScoredByAndGenre: " + n + " " + reviewerType + " " + genre);
        Set<Movie> movies = getMovieBy(reviewerType, genre, null);
        if (movies == null || movies.size() <= 0) {
            System.out.println("No movie by Reviewer");
            return null;
        }
        List<Movie> movieByScore = new ArrayList<>(movies);
        if (movieByScore != null && movieByScore.size() > 0) {
            List<Movie> m = sortAndGetTop(n, reviewerType, movieByScore);
            System.out.println(m);

            return m;
        }
        System.out.println("No Movie review in :" + genre);
        return null;
    }

    private List<Movie> sortAndGetTop(int n, ReviewerType reviewerType, List<Movie> movieByScore) {
        Collections.sort(movieByScore, getComparator(reviewerType));
        List<Movie> m = movieByScore.subList(0, getMin(n, movieByScore.size()));
        return m;
    }

    @Override
    public float averageSore(int releaseOn) {
        Set<Movie> movies = movieService.getMovieByReleaseYear(releaseOn);
        if (movies != null || movies.size() > 0) {
            float average = averageScore(movies);
            System.out.println(movies);
            System.out.print("averageSore in " + releaseOn + " is ");
            System.out.println(average);
            return average;
        }
        System.out.println("No movie released in Year : " + releaseOn);
        return -1;
    }

    @Override
    public float averageSore(Genre genre) {
        Set<Movie> movies = movieService.getMovies(genre);
        if (movies != null || movies.size() > 0) {
            float average = averageScore(movies);
            System.out.println(movies);
            System.out.print("averageSore in " + genre + " is ");
            System.out.println(average);
            return average;

        }
        System.out.println("No movie of Genre : " + genre);
        return -1;
    }

    private float averageScore(Set<Movie> movies) {
        float score = 0;
        float reviewCount = 0;
        for (Movie m : movies) {
            if (!m.hasReleased()) continue;
            score += m.getTotalScore();
            reviewCount += m.getReviewCount();
        }
        return reviewCount == 0 ? 0 : score / reviewCount;
    }

    private boolean isValidScore(int score) {
        return score >= 1 && score <= 10;
    }

    private Comparator<Movie> getComparator(ReviewerType reviewerType) {
        if (reviewerType == ReviewerType.CRITIC) {
            return new Comparator<Movie>() {
                @Override
                public int compare(Movie o2, Movie o1) {
                    return o1.getCriticScore().compareTo(o2.getCriticScore());
                }
            };

        } else if (reviewerType == ReviewerType.VIEWER) {
            return new Comparator<Movie>() {
                @Override
                public int compare(Movie o2, Movie o1) {
                    return o1.getUserScore().compareTo(o2.getUserScore());
                }
            };

        } else {
            return new Comparator<Movie>() {
                @Override
                public int compare(Movie o2, Movie o1) {
                    return ((Integer) (o1.getUserScore() + o1.getCriticScore())).compareTo((o2.getUserScore() + o2.getCriticScore()));
                }
            };

        }

    }

    private boolean hasReviewed(User u, Movie m) {
        Set<Movie> movies = userMovieReviewed.get(u.getName());
        return movies != null && movies.contains(m);

    }

    @Override
    public void reviewMovie(User user, Movie movie, int score) {
        int finalScore = ReviewerType.VIEWER == user.getReviewerType() ? score : 2 * score;
        movieService.updateScore(movie.getName(), user.getReviewerType(), finalScore);
        if (reviewerMovies.get(user.getReviewerType()) == null) {
            List<Movie> movies = new ArrayList<>();
            reviewerMovies.put(user.getReviewerType(), movies);
        }
        reviewerMovies.get(user.getReviewerType()).add(movie);

        if (userMovieReviewed.get(user.getName()) == null) {
            Set<Movie> movies = new HashSet<>();
            userMovieReviewed.put(user.getName(), movies);
        }
        userMovieReviewed.get(user.getName()).add(movie);
        System.out.println(movie.getName() + " is reviewed by " + user.getName() + " with score " + finalScore);
    }

    @Override
    public void changeTypeIfRequired(String name) {
        Set<Movie> userMovie = userMovieReviewed.get(name);
        if (userMovie != null && userMovie.size() == 3) {
            if (userService.changeUserType(name, ReviewerType.CRITIC)) {
                System.out.println(name + " has been upgraded as CRITIC");
            }

        }

    }
    private Set<Movie> getMovieBy(ReviewerType type, Genre genre, Integer year){

        if (type != null && reviewerMovies.get(type) != null && genre != null  && year != null) {
            return reviewerMovies.get(type).stream().filter(m -> m.getGenre() == genre).filter(m -> m.getReleaseOn().equals(year)).collect(Collectors.toSet());
        }
        if (type != null && reviewerMovies.get(type) != null && genre != null && year == null) {
            return reviewerMovies.get(type).stream().filter(m -> m.getGenre() == genre).collect(Collectors.toSet());

        }
        if (type != null && reviewerMovies.get(type) != null && genre==null && year != null) {
            return reviewerMovies.get(type).stream().filter(m -> m.getReleaseOn().equals(year)).collect(Collectors.toSet());
        }
        if (type != null && reviewerMovies.get(type) != null && genre == null && year == null) {
            return reviewerMovies.get(type).stream().collect(Collectors.toSet());

        }
        if (type == null && genre != null && year != null) {
            return movieService.getMovies(genre).stream().filter(m -> m.getReleaseOn().equals(year)).collect(Collectors.toSet());

        }
        if (type == null && genre != null && year == null) {
            return movieService.getMovies(genre);

        }
        if (type == null && genre == null && year != null) {
            return movieService.getMovieByReleaseYear(year);
        }
        return null;

    }

    private int getMin(int a, int b) {
        return a > b ? b : a;
    }
}
