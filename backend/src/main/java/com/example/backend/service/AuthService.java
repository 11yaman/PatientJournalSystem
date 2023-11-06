package com.example.backend.service;

import com.example.backend.model.User;

public interface AuthService {
    User authenticate(String username, String password);
    void logout();
    User register(User userToRegister);
}
