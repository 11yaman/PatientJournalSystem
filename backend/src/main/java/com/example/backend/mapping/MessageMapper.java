package com.example.backend.mapping;

import com.example.backend.dto.response.MessageDto;
import com.example.backend.dto.response.PatientDto;
import com.example.backend.dto.response.ReplyDto;
import com.example.backend.dto.response.UserDto;
import com.example.backend.model.Message;
import com.example.backend.model.Patient;
import com.example.backend.model.Reply;
import com.example.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageMapper implements StrategyMapper<Message, MessageDto> {
    @Override
    public MessageDto map(Message source) {
        User sender = source.getSender();
        UserDto senderDto = new UserDto(sender.getId(), sender.getUsername(),
                sender.getFirstName(), sender.getLastName(), sender.getRole().name());
        return new MessageDto(source.getId(), source.getContent(), source.getDateTime(), senderDto, source.getStatus());
    }

    @Override
    public List<MessageDto> mapAll(List<Message> source) {
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message m:
                source) {
            messageDtos.add(map(m));
        }
        return messageDtos;
    }

}
