package com.example.backend.service.impl;

import com.example.backend.model.Patient;
import com.example.backend.repository.PatientRepository;
import com.example.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return (List<Patient>) patientRepository.findAll();
    }
}