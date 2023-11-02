package com.example.backend.model;

import jakarta.persistence.*;

@Entity
public class Employee extends User{
    private Position position;
    public enum Position {DOCTOR, OTHER}

    public Employee() {
    }

    public Employee(String firstName, String lastName, String userName, String password, Position position) {
        super(firstName, lastName, userName, password, Role.EMPLOYEE);
        this.position = position;
    }
}
