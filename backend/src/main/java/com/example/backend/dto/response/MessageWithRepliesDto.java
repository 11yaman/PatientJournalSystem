package com.example.backend.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record MessageWithRepliesDto(Long id, String content, LocalDateTime dateTime,
                         UserDto senderDto, List<ReplyDto> replies) {
}
