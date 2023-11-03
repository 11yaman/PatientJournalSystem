package com.example.backend.controller;

import com.example.backend.dto.PatientDto;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.mapping.StrategyMapper;
import com.example.backend.model.Patient;
import com.example.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * handles requests by employees related to patients, only authorized by employees
 */
@RestController
@RequestMapping("/patients")
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class PatientsController {
    private final PatientService patientService;
    private final StrategyMapper<Patient, PatientDto> strategyMapper;

    @Autowired
    public PatientsController(PatientService patientService, StrategyMapper<Patient, PatientDto> strategyMapper) {
        this.patientService = patientService;
        this.strategyMapper = strategyMapper;
    }

    @GetMapping
    public List<PatientDto> getAllPatients() {
        return strategyMapper.mapAll(patientService.getAllPatients());
    }

    @GetMapping("/{patientId}")
    public PatientDto getPatientInfoById(@PathVariable Long patientId) {
        try {
            return strategyMapper.map(patientService.getPatientById(patientId));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}