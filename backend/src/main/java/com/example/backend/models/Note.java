package com.example.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Note {
    @Id
    private Long id;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Patient patient;
    private String observation;
    private String patientCondition;
    private LocalDateTime encounter;
}
