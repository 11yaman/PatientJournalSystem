package com.example.backend.repository;

import com.example.backend.model.Message;
import com.example.backend.model.Patient;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findBySender(User sender);
    List<Message> findBySenderId(Long senderId);
    List<Message> findByStatus(Message.Status status);
}
