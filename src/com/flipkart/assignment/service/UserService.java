package com.flipkart.assignment.service;

import com.flipkart.assignment.enums.Genre;
import com.flipkart.assignment.enums.ReviewerType;
import com.flipkart.assignment.model.Movie;
import com.flipkart.assignment.model.User;

import java.util.List;

public interface UserService {
    boolean addUser(String name);

    User getUser(String name);

    boolean changeUserType(String name, ReviewerType reviewerType);

}
