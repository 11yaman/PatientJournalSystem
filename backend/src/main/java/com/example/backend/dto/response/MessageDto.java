package com.example.backend.dto.response;

import java.time.LocalDateTime;

public record MessageDto(Long id, String content, LocalDateTime dateTime,
                         UserDto senderDto) {
}
