package com.example.backend.service;

import com.example.backend.model.Message;
import com.example.backend.model.Reply;

import java.util.List;

public interface MessageService {
    Message createMessage(Message message);

    Message getMessageWithRepliesById(Long messageId);

    Message replyToMessage(Message parentMessage, Reply reply);

    List<Message> getAllMessagesByPatient(Long patientId);

    List<Message> getActiveMessagesForEmployees();
}
