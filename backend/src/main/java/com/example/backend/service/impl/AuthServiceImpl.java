package com.example.backend.service.impl;

import com.example.backend.exception.UserNotFoundException;
import com.example.backend.model.User;
import com.example.backend.service.AuthService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public User register(User userToRegister) {
        userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
        return userService.createUser(userToRegister);
    }

    @Override
    public User authenticate(String username, String password) {
        try{
            Authentication authenticationResponse = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
            return userService.findByUsername(username);
        } catch (AuthenticationException e) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
