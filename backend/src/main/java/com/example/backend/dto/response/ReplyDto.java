package com.example.backend.dto.response;

import java.time.LocalDateTime;

public record ReplyDto(Long id, String content, LocalDateTime dateTime, UserDto userDto) {
}
