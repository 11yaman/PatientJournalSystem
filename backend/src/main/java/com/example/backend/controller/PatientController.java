package com.example.backend.controller;

import com.example.backend.dto.request.MessageRequest;
import com.example.backend.dto.response.MessageDto;
import com.example.backend.dto.response.PatientDto;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.mapping.StrategyMapper;
import com.example.backend.model.Message;
import com.example.backend.model.Patient;
import com.example.backend.security.UserDetailsImpl;
import com.example.backend.service.MessageService;
import com.example.backend.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('PATIENT')")
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final MessageService messageService;
    private final StrategyMapper<Patient, PatientDto> patientMapper;
    private final StrategyMapper<Message, MessageDto> messageMapper;

    public PatientController(MessageService messageService, PatientService patientService, StrategyMapper<Patient, PatientDto> patientMapper, StrategyMapper<Message, MessageDto> messageMapper) {
        this.messageService = messageService;
        this.patientService = patientService;
        this.patientMapper = patientMapper;
        this.messageMapper = messageMapper;
    }

    @GetMapping("/info")
    public ResponseEntity<PatientDto> getUserInfo(Authentication authentication) {
        try {
            Patient patient = patientService.getPatientByUsername(authentication.getName());
            return ResponseEntity.ok(patientMapper.map(patient));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @RequestMapping("/messages")
    public ResponseEntity<List<MessageDto>> getUserMessages(Authentication authentication) {
        try {
            List<Message> userMessages = messageService.getAllMessagesByPatientId(
                    ((UserDetailsImpl) authentication.getPrincipal()).getId());
            return ResponseEntity.ok(messageMapper.mapAll(userMessages));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
