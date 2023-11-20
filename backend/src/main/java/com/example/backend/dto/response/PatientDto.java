package com.example.backend.dto.response;

import java.time.LocalDate;

public record PatientDto(Long id, String email, String firstName, String lastName, LocalDate birthDate) {
}
