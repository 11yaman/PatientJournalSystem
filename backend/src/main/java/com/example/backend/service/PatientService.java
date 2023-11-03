package com.example.backend.service;

import com.example.backend.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();

    Patient getPatientById(Long patientId);

    Patient getPatientByUsername(String name);
}
