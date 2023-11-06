package com.example.backend.service.impl;

import com.example.backend.model.Message;
import com.example.backend.model.Patient;
import com.example.backend.model.Reply;
import com.example.backend.model.User;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.ReplyRepository;
import com.example.backend.service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ReplyRepository replyRepository) {
        this.messageRepository = messageRepository;
        this.replyRepository = replyRepository;
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
    public Message getMessageWithRepliesById(Long messageId) {
        return messageRepository.findMessageWithRepliesById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));
    }

    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));
    }

    @Override
    public Message replyToMessage(Message parentMessage, Reply reply) {
        parentMessage.getReplies().add(reply);
        parentMessage.setStatus(Message.Status.CURRENT);
        messageRepository.save(parentMessage);
        return parentMessage;
    }

    @Override
    public List<Message> getAllMessagesByPatient(User user) {
        return messageRepository.findBySender(user);
    }

    @Override
    public List<Message> getAllMessagesByPatientId(Long patientId) {
        return messageRepository.findBySenderId(patientId);
    }
    @Override
    public List<Message> getCurrentMessagesForEmployees() {
        return messageRepository.findByStatus(Message.Status.CURRENT);
    }

}
