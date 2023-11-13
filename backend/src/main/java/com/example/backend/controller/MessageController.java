package com.example.backend.controller;

import com.example.backend.dto.request.MessageRequest;
import com.example.backend.dto.response.MessageDto;
import com.example.backend.dto.response.MessageWithRepliesDto;
import com.example.backend.dto.response.ReplyDto;
import com.example.backend.dto.response.UserDto;
import com.example.backend.mapping.StrategyMapper;
import com.example.backend.model.Message;
import com.example.backend.model.Reply;
import com.example.backend.model.User;
import com.example.backend.service.MessageService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final StrategyMapper<Message, MessageDto> messageMapper;
    private final StrategyMapper<User, UserDto> userMapper;

    @Autowired
    public MessageController(MessageService messageService,
                             UserService userService,
                             StrategyMapper<Message, MessageDto> messageMapper,
                             StrategyMapper<User, UserDto> userMapper) {
        this.messageService = messageService;
        this.userService = userService;
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
    }

    @PostMapping("/messages")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<MessageDto> createMessageByPatient(@RequestBody MessageRequest messageRequest,
                                                             Authentication authentication) {
        try {
            User loggedInUser = userService.getUserByUsername(authentication.getName());
            Message createdMessage = messageService.createMessage(
                    new Message(messageRequest.content(), loggedInUser));
            return new ResponseEntity<>(messageMapper.map(createdMessage), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Message could not be created");
        }
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<MessageWithRepliesDto> getMessageWithReplies(@PathVariable Long messageId,
                                                                Authentication authentication) {
        try {
            Message message = messageService.getMessageWithRepliesById(messageId);

            if(!userService.isEmployeeOrResourceOwner(authentication, message.getSender().getId()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            return ResponseEntity.ok(
                    new MessageWithRepliesDto(message.getId(), message.getContent(), message.getDateTime(),
                            userMapper.map(message.getSender()), message.getStatus(),
                            mapToReplyDtos(message.getReplies())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/messages/{messageId}/reply")
    public ResponseEntity<MessageWithRepliesDto> replyToMessage(
            @PathVariable Long messageId,
            @RequestBody MessageRequest replyRequest,
            Authentication authentication
    ) {
        try {
            Message parentMessage = messageService.getMessageWithRepliesById(messageId);
            User loggedInUser = userService.getUserByUsername(authentication.getName());

            if(!userService.isEmployeeOrResourceOwner(authentication, parentMessage.getSender().getId()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            Message repliedMessage = messageService.replyToMessage(parentMessage,
                    new Reply(replyRequest.content(), loggedInUser));
            return ResponseEntity.ok(
                    new MessageWithRepliesDto(repliedMessage.getId(), repliedMessage.getContent(), repliedMessage.getDateTime(),
                            userMapper.map(repliedMessage.getSender()), repliedMessage.getStatus(),
                            mapToReplyDtos(repliedMessage.getReplies())));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Message could not be replied");
        }
    }

    @RequestMapping("/patients/{patientId}/messages/list")
    public ResponseEntity<List<MessageDto>> getPatientMessages(Authentication authentication,
                                                                @PathVariable Long patientId){
        if(!userService.isEmployeeOrResourceOwner(authentication, patientId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            List<Message> userMessages;
            if (userService.isEmployee(authentication))
                userMessages = messageService.getAllMessagesByPatient(patientId);
            else
                userMessages = messageService.getAllMessagesByPatient(
                        userService.getAuthenticatedUserId(authentication));
            return ResponseEntity.ok(messageMapper.mapAll(userMessages));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
    }

    @GetMapping("/messages/active")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<MessageDto>> getActiveMessages() {
        try {
            List<Message> activeMessages = messageService.getActiveMessagesForEmployees();
            return ResponseEntity.ok(messageMapper.mapAll(activeMessages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private List<ReplyDto> mapToReplyDtos(List<Reply> replies) {
        List<ReplyDto> replyDtos = new ArrayList<>();
        for (Reply r: replies) {
            User replySender = r.getSender();
            replyDtos.add(new ReplyDto(r.getId(), r.getContent(), r.getDateTime(),
                    new UserDto(replySender.getId(), replySender.getUsername(),
                            replySender.getFirstName(), replySender.getLastName(), replySender.getRole().name())));
        }
        return replyDtos;
    }

}
