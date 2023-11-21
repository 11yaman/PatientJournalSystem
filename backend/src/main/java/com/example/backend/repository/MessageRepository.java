package com.example.backend.repository;

import com.example.backend.model.Message;
import com.example.backend.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findBySender(User sender);
    List<Message> findBySenderIdOrderByDateTimeDesc(Long senderId);
    List<Message> findByStatusOrderByDateTimeDesc(Message.Status status);
}
