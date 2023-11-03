package com.example.backend.controller;

import com.example.backend.dto.PatientDto;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.mapping.StrategyMapper;
import com.example.backend.model.Patient;
import com.example.backend.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * handles requests by a single logged-in patient, only authorized by the patient
 */
@RestController
@RequestMapping("/patient")
@PreAuthorize("hasAuthority('PATIENT')")
public class PatientController {

    private final PatientService patientService;
    private final StrategyMapper<Patient, PatientDto> strategyMapper;

    public PatientController(PatientService patientService, StrategyMapper<Patient, PatientDto> strategyMapper) {
        this.patientService = patientService;
        this.strategyMapper = strategyMapper;
    }

    @GetMapping("/info")
    public PatientDto getPatientInfo(Authentication authentication) {
        try {
            return strategyMapper.map(patientService.getPatientByUsername(authentication.getName()));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
