package com.example.backend.service;

import com.example.backend.model.Message;
import com.example.backend.model.Patient;
import com.example.backend.model.Reply;
import com.example.backend.model.User;

import java.util.List;

public interface MessageService {
    Message createMessage(Message message);

    Message getMessageWithRepliesById(Long messageId);

    Message getMessageById(Long messageId);

    Message replyToMessage(Message parentMessage, Reply reply);

    List<Message> getAllMessagesByPatient(User user);

    List<Message> getAllMessagesByPatientId(Long patientId);

    List<Message> getCurrentMessagesForEmployees();
}
