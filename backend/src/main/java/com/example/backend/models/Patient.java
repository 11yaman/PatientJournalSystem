package com.example.backend.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Patient extends User{
    @OneToMany(mappedBy = "patient")
    private List<Note> notes;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password, Role.PATIENT);
    }
}
