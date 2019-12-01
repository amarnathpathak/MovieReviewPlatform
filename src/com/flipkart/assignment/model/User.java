package com.flipkart.assignment.model;

import com.flipkart.assignment.enums.ReviewerType;

import java.util.*;

/**
 * @author amarnath.pathak
 * @date 01/12/19
 **/
public class User {
    private String name;
    private ReviewerType reviewerType;

    public User(String name, ReviewerType type) {
        this.name = name;
        this.reviewerType = type;
    }

    public String getName() {
        return name;
    }

    public ReviewerType getReviewerType() {
        return reviewerType;
    }

    public void setReviewerType(ReviewerType reviewerType) {
        this.reviewerType = reviewerType;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
