package com.example.backend.dto.response;

import com.example.backend.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public record MessageWithRepliesDto(Long id, String content, LocalDateTime dateTime,
                                    UserDto sender, Message.Status status, List<ReplyDto> replies) {
}
