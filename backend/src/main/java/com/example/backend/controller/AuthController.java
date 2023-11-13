package com.example.backend.controller;

import com.example.backend.dto.request.AuthenticateRequest;
import com.example.backend.dto.request.RegisterRequest;
import com.example.backend.dto.response.UserDto;
import com.example.backend.exception.DuplicatedUserInfoException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.mapping.StrategyMapper;
import com.example.backend.model.Patient;
import com.example.backend.model.User;
import com.example.backend.service.AuthService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final StrategyMapper<User, UserDto> userMapper;
    @Autowired
    public AuthController(AuthService authService, UserService userService, StrategyMapper<User, UserDto> userMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserDto> authenticate(@RequestBody(required = false) AuthenticateRequest authenticateRequest,
                                                Authentication authentication) {
        try {
            User user;
            if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser"))
                user = userService.getUserByUsername(authentication.getName());
            else if (authenticateRequest != null)
                user = authService.authenticate(authenticateRequest.email(), authenticateRequest.password());
            else
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect user ID or password");

            return new ResponseEntity<>(userMapper.map(user), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect user ID or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerPatient(@RequestBody RegisterRequest registerRequest) {
        try {
            if(registerRequest.email().isBlank() || registerRequest.password().isBlank() ||
                    registerRequest.firstName().isBlank() || registerRequest.lastName().isBlank())
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid input");

            User user = userService.createUser(new Patient(registerRequest.email(), registerRequest.password(),
                    registerRequest.firstName(), registerRequest.lastName()));
            return new ResponseEntity<>(userMapper.map(user), HttpStatus.CREATED);
        } catch (DuplicatedUserInfoException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Email %s already has been used", registerRequest.email()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid input");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return new ResponseEntity<>("Logged out", HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            return ResponseEntity.ok(userMapper.map(user));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
    }
}