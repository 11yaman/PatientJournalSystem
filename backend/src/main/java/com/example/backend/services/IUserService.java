package com.example.backend.services;

import com.example.backend.models.User;
import org.springframework.stereotype.Service;

public interface IUserService {
    User findByUsername(String username);

    void createUser(User user);
}
