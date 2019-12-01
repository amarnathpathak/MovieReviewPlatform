package com.flipkart.assignment.model;

import com.flipkart.assignment.enums.Genre;

import java.util.Date;
import java.util.Objects;

/**
 * @author amarnath.pathak
 * @date 01/12/19
 **/
public class Movie {

    private String name;
    private Integer releaseOn;
    private Genre genre;
    private Integer userScore;
    private Integer criticScore;
    private Integer totalScore;
    private Integer reviewCount;


    public Movie(String name, Integer releaseOn, Genre genre) {
        this.name = name;
        this.releaseOn = releaseOn;
        this.genre = genre;
        userScore = 0;
        criticScore = 0;
        totalScore = 0;
        this.reviewCount = 0;
    }

    public String getName() {
        return name;
    }

    public Integer getReleaseOn() {
        return releaseOn;
    }

    public Genre getGenre() {
        return genre;
    }

    public void incUserScore(int score) {
        userScore += score;
        totalScore+=score;
    }

    public void incCriticScore(int score) {
        criticScore += score;
        totalScore+=score;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public Integer getCriticScore() {
        return criticScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void incReviewCount(int count) {
        reviewCount += count;
    }
    public void incReviewCount() {
        reviewCount ++;
    }

    public boolean hasReleased() {
        return this.releaseOn <= 2019;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getName(), movie.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Movie{");
        sb.append("name='").append(name).append('\'');
        sb.append(", releaseOn=").append(releaseOn);
        sb.append(", genre=").append(genre);
        sb.append(", userScore=").append(userScore);
        sb.append(", criticScore=").append(criticScore);
        sb.append(", totalScore=").append(totalScore);
        sb.append('}');
        return sb.toString();
    }
}
