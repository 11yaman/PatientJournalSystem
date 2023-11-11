package com.example.backend.service.impl;

import com.example.backend.model.Encounter;
import com.example.backend.repository.EncounterRepository;
import com.example.backend.service.EncounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncounterServiceImpl implements EncounterService {
    private final EncounterRepository encounterRepository;

    @Autowired
    public EncounterServiceImpl(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    @Override
    public Encounter getEncounterById(Long encounterId) {
        //return encounterRepository.findById(encounterId).orElse(null);
        return null;
    }
}
