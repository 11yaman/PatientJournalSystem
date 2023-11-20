package com.example.backend.dto.request;

import com.example.backend.model.Employee;

import java.time.LocalDate;

public record CreateEmployeeRequest(String email, String password,
                                    String firstName, String lastName,
                                    LocalDate birthDate, String position) {
}