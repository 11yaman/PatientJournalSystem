package com.example.backend.mapping;

import com.example.backend.model.Patient;
import com.example.backend.dto.response.PatientDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatientMapper implements StrategyMapper<Patient, PatientDto> {

    @Override
    public PatientDto map(Patient source) {
        return new PatientDto(source.getId(), source.getUsername(),source.getFirstName(),source.getLastName());
    }

    @Override
    public List<PatientDto> mapAll(List<Patient> source) {
        List<PatientDto> patientDtos = new ArrayList<>();
        for (Patient p:
             source) {
            patientDtos.add(map(p));
        }
        return patientDtos;
    }
}
