package com.example.backend.mapping;

import com.example.backend.dto.response.EncounterDto;
import com.example.backend.dto.response.MessageDto;
import com.example.backend.dto.response.NoteDto;
import com.example.backend.dto.response.UserDto;
import com.example.backend.model.Encounter;
import com.example.backend.model.Message;
import com.example.backend.model.Note;
import com.example.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoteMapper implements StrategyMapper<Note, NoteDto> {
    private final StrategyMapper<Encounter, EncounterDto> encounterMapper;
    private final StrategyMapper<User, UserDto> userMapper;

    @Autowired
    public NoteMapper(EncounterMapper encounterMapper, StrategyMapper<User, UserDto> userMapper) {
        this.encounterMapper = encounterMapper;
        this.userMapper = userMapper;
    }

    @Override
    public NoteDto map(Note source) {
        return new NoteDto(source.getId(), source.getText(), source.getDateTimeCreated(),
                userMapper.map(source.getEmployee()), userMapper.map(source.getPatient()),
                encounterMapper.map(source.getEncounter()));
    }

    @Override
    public List<NoteDto> mapAll(List<Note> source) {
        List<NoteDto> noteDtos = new ArrayList<>();
        for (Note n:
                source) {
            noteDtos.add(map(n));
        }
        return noteDtos;
    }
}
