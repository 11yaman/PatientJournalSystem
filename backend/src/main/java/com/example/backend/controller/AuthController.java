package com.example.backend.controller;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.SignUpRequest;
import com.example.backend.model.Patient;
import com.example.backend.model.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    //TODO: TDOs + mappers
    @PostMapping("/authenticate")
    public ResponseEntity<? extends User> authenticate(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authenticationResponse = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

            final User user = userService.findByUsername(loginRequest.username());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect user ID or password");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity<String> signUpNewPatient(@RequestBody SignUpRequest signUpRequest) {
        if (userService.findByUsername(signUpRequest.username()) != null)
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, String.format("Username %s already been used", signUpRequest.username()));

        User createdUser = userService.createUser(new Patient(
                signUpRequest.firstName(), signUpRequest.lastName(), signUpRequest.username(), signUpRequest.password()));

        return new ResponseEntity<>("Created", HttpStatus.OK);
    }
}