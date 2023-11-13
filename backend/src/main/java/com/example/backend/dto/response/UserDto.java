package com.example.backend.dto.response;

public record UserDto(Long id, String email, String firstName, String lastName, String role) {
}
