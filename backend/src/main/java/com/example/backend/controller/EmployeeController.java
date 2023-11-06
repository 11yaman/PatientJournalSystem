package com.example.backend.controller;

import com.example.backend.dto.request.MessageRequest;
import com.example.backend.dto.response.MessageDto;
import com.example.backend.mapping.StrategyMapper;
import com.example.backend.model.Message;
import com.example.backend.model.Reply;
import com.example.backend.model.User;
import com.example.backend.security.UserDetailsImpl;
import com.example.backend.service.MessageService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('EMPLOYEE')")
@RequestMapping("/employee")
public class EmployeeController {

}
