package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
//TODO: prel
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
