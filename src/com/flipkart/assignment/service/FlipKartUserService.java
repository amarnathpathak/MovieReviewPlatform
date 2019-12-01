package com.flipkart.assignment.service;

import com.flipkart.assignment.enums.ReviewerType;
import com.flipkart.assignment.model.Movie;
import com.flipkart.assignment.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sun.tools.javac.util.StringUtils.toLowerCase;

/**
 * @author amarnath.pathak
 * @date 01/12/19
 **/
public class FlipKartUserService implements UserService {

    private final Map<String, User> users = new HashMap<>();
    private ReviewerType defaultUserType = ReviewerType.VIEWER;
    private static UserService instance;

    private FlipKartUserService(){}

    public static UserService getInstance() {
        if (instance == null) {

            synchronized (FlipKartUserService.class) {
                if (instance == null) {
                    instance = new FlipKartUserService();
                }
            }
        }
        return instance;
    }


    @Override
    public boolean addUser(String name) {
        if (users.containsKey(toLowerCase(name))) {
            System.out.println("User Already Registered");
            return false;
        }
        users.put(toLowerCase(name), new User(name, defaultUserType));
        System.out.println("User Added : " + name);
        return true;
    }

    @Override
    public User getUser(String name) {
        if (!users.containsKey(toLowerCase(name))) {
            System.out.println("No User Registered with name: " + name);
            return null;
        }
        return users.get(toLowerCase(name));
    }

    @Override
    public boolean changeUserType(String name, ReviewerType reviewerType) {
        if (!users.containsKey(toLowerCase(name))) {
            System.out.println("No User Registered with name: " + name);
            return false;
        }
        User user = users.get(toLowerCase(name));
        if (user.getReviewerType().equals(reviewerType)) {
            System.out.println("User: " + name + " is already a : " + reviewerType);
            return false;
        }
        if (user.getReviewerType().compareTo(reviewerType) < 0) {
            user.setReviewerType(reviewerType);
        }
        return true;
    }

}
