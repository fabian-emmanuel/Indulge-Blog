package com.codewithfibbee.blog_api.service;

import com.codewithfibbee.blog_api.models.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    User getUserByUsername(String userName);

    List<User> getAllUsers();

    User getUserById(Long userId);

    void delete(User user);

    User getUserByEmailAndPassword(String email, String password);

    void deactivateUserScheduler();

    void undoDelete(User user);
}
