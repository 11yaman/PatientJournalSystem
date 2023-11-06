package com.example.backend.controller;

import com.example.backend.dto.response.MessageDto;
import com.example.backend.dto.response.PatientDto;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.mapping.StrategyMapper;
import com.example.backend.model.Message;
import com.example.backend.model.Patient;
import com.example.backend.service.MessageService;
import com.example.backend.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/patients")
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class PatientRecordsController {
    private final PatientService patientService;
    private final MessageService messageService;
    private final StrategyMapper<Patient, PatientDto> patientMapper;
    private final StrategyMapper<Message, MessageDto> messageMapper;

    public PatientRecordsController(PatientService patientService, MessageService messageService, StrategyMapper<Patient, PatientDto> patientMapper, StrategyMapper<Message, MessageDto> messageMapper) {
        this.patientService = patientService;
        this.messageService = messageService;
        this.patientMapper = patientMapper;
        this.messageMapper = messageMapper;
    }

    @GetMapping("/list")
    public List<PatientDto> getAllPatients() {
        return patientMapper.mapAll(patientService.getAllPatients());
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientInfoById(@PathVariable Long patientId) {
        try {
            Patient patient = patientService.getPatientById(patientId);
            return ResponseEntity.ok(patientMapper.map(patient));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
    @GetMapping("/{patientId}/messages")
    public ResponseEntity<List<MessageDto>> getAllMessagesByPatient(@PathVariable Long patientId) {
        try {
            List<Message> userMessages = messageService.getAllMessagesByPatientId(patientId);
            return ResponseEntity.ok(messageMapper.mapAll(userMessages));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
