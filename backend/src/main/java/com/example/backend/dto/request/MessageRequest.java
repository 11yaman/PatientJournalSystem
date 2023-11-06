package com.example.backend.dto.request;

import java.time.LocalDateTime;

public record MessageRequest(String content, LocalDateTime dateTime) {
}
