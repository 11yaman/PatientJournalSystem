package com.example.backend.mapping;

import com.example.backend.dto.response.EncounterDto;
import com.example.backend.model.Encounter;
import org.springframework.stereotype.Component;

import java.util.List;
//TODO
@Component
public class EncounterMapper implements StrategyMapper<Encounter, EncounterDto> {
    @Override
    public EncounterDto map(Encounter source) {
        return null;
    }

    @Override
    public List<EncounterDto> mapAll(List<Encounter> source) {
        return null;
    }
}
