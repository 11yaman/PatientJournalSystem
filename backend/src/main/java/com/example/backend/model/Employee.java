package com.example.backend.model;

import jakarta.persistence.*;

@Entity
public class Employee extends User{
    private Position position;
    public enum Position {DOCTOR, OTHER}

    public Employee() {
    }

    public Employee(String userName, String password, String firstName, String lastName, Position position) {
        super(userName, password, firstName, lastName, Role.EMPLOYEE);
        this.position = position;
    }
}
