package com.example.backend.controller;

import com.example.backend.model.Patient;
import com.example.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    //TODO: TDOs
    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }
}