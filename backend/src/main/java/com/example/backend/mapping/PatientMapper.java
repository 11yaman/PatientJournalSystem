package com.example.backend.mapping;

import com.example.backend.model.Patient;
import com.example.backend.dto.PatientDto;
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
    public Patient mapReverse(PatientDto source) {
        return new Patient(source.id(), source.username(), source.firstName(), source.lastName());
    }

    @Override
    public List<PatientDto> mapAll(List<Patient> source) {
        List<PatientDto> collection = new ArrayList<>();
        for (Patient p:
             source) {
            collection.add(map(p));
        }
        return collection;
    }

    @Override
    public List<Patient> mapAllReverse(List<PatientDto> source) {
        List<Patient> collection = new ArrayList<>();
        for (PatientDto pDto:
             source) {
            collection.add(mapReverse(pDto));
        }
        return collection;
    }
}
