package com.example.backend.controller;

import com.example.backend.dto.request.RegisterRequest;
import com.example.backend.dto.response.UserDto;
import com.example.backend.mapping.StrategyMapper;
import com.example.backend.model.Employee;
import com.example.backend.model.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@PreAuthorize("hasAuthority('EMPLOYEE')")
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final UserService userService;
    private final StrategyMapper<User, UserDto> userMapper;

    @Autowired
    public EmployeeController(UserService userService, StrategyMapper<User, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createEmployee(@RequestBody RegisterRequest registerRequest,
                                                  Authentication authentication) {
        try {
            User user = userService.createUser(
                    new Employee(registerRequest.email(), registerRequest.password(),
                            registerRequest.firstName(), registerRequest.lastName(),
                            Employee.Position.OTHER));
            return new ResponseEntity<>(userMapper.map(user), HttpStatus.CREATED);

        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User could not be created");
        }
    }
}
