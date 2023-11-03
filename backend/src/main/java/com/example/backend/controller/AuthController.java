package com.example.backend.controller;

import com.example.backend.dto.AuthenticateRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.dto.UserDto;
import com.example.backend.exception.DuplicatedUserInfoException;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.model.Patient;
import com.example.backend.model.User;
import com.example.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserDto> authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        try {
            User user = authService.authenticate(authenticateRequest.username(), authenticateRequest.password());
            return new ResponseEntity<>(mapToUserDto(user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect user ID or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return new ResponseEntity<>("Logged out", HttpStatus.OK);
    }

    @PostMapping("/register-patient")
    public ResponseEntity<UserDto> registerPatient(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = authService.register(new Patient(
                    registerRequest.username(), registerRequest.password(), registerRequest.firstName(), registerRequest.lastName()));
            return new ResponseEntity<>(mapToUserDto(user), HttpStatus.CREATED);
        } catch (DuplicatedUserInfoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("Username %s already been used", registerRequest.username()));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    private UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
    }
}