package com.example.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Patient extends User{
    @OneToMany(mappedBy = "patient")
    private List<Note> notes;

    public Patient() {
    }

    public Patient(Long id, String username, String firstName, String lastName) {
        super(id, username, firstName, lastName, Role.PATIENT);
    }

    public Patient(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName, Role.PATIENT);
    }
}
