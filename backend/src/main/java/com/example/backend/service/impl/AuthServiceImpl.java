package com.example.backend.service.impl;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.User;
import com.example.backend.service.AuthService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public User authenticate(String username, String password) {
        try{
            Authentication authenticationResponse = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
            return userService.getUserByUsername(username);
        } catch (AuthenticationException e) {
            throw new NotFoundException("User not found: " + username);
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
