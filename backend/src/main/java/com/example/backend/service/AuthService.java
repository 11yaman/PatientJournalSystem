package com.example.backend.service;

import com.example.backend.dto.AuthenticateRequest;
import com.example.backend.dto.PatientDto;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.dto.UserDto;
import com.example.backend.model.Employee;
import com.example.backend.model.Patient;
import com.example.backend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    User authenticate(String username, String password);
    void logout();
    User register(User userToRegister);
}
