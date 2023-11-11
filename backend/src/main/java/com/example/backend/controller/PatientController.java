package com.example.backend.controller;

import com.example.backend.dto.request.RegisterRequest;
import com.example.backend.dto.response.PatientDto;
import com.example.backend.dto.response.UserDto;
import com.example.backend.exception.DuplicatedUserInfoException;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.mapping.StrategyMapper;
import com.example.backend.model.Patient;
import com.example.backend.model.User;
import com.example.backend.service.PatientService;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    private final PatientService patientService;
    private final StrategyMapper<Patient, PatientDto> patientMapper;
    private final UserService userService;
    private final StrategyMapper<User, UserDto> userMapper;

    public PatientController(PatientService patientService,
                             UserService userService,
                             StrategyMapper<Patient, PatientDto> patientMapper,
                             StrategyMapper<User, UserDto> userMapper) {
        this.patientService = patientService;
        this.userService = userService;
        this.patientMapper = patientMapper;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatient(Authentication authentication, @PathVariable Long id) {
        if(!userService.isEmployeeOrResourceOwner(authentication, id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            Patient patient;
            if (userService.isEmployee(authentication))
                patient = patientService.getPatientById(id);
            else
                patient = patientService.getPatientByUsername(authentication.getName());
            return ResponseEntity.ok(patientMapper.map(patient));

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerPatient(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.createUser(new Patient(registerRequest.username(), registerRequest.password(),
                    registerRequest.firstName(), registerRequest.lastName()));
            return new ResponseEntity<>(userMapper.map(user), HttpStatus.CREATED);
        } catch (DuplicatedUserInfoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    String.format("Username %s already been used", registerRequest.username()));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public List<PatientDto> getAllPatients() {
        return patientMapper.mapAll(patientService.getAllPatients());
    }
}
