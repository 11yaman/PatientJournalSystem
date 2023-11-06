package com.example.backend.service;

import com.example.backend.model.User;

public interface UserService {
    User getUserByUsername(String username);

    User createUser(User user);

    User getUserById(Long userId);
}
