package com.example.backend.dto.request;

public record RegisterRequest(String email, String password, String firstName, String lastName) {
}
