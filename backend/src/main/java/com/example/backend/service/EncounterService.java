package com.example.backend.service;

import com.example.backend.model.Encounter;
import org.springframework.stereotype.Service;

@Service
public interface EncounterService {
    Encounter getEncounterById(Long encounterId);
}
