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
import com.example.backend.security.UserDetailsImpl;
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

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final StrategyMapper<Message, MessageDto> messageMapper;

    @Autowired
    public MessageController(MessageService messageService, UserService userService,
                             StrategyMapper<Message, MessageDto> messageMapper) {
        this.messageService = messageService;
        this.userService = userService;
        this.messageMapper = messageMapper;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<MessageDto> createMessageByPatient(@RequestBody MessageRequest messageRequest,
                                                             Authentication authentication) {
        User loggedInUser = userService.getUserByUsername(authentication.getName());
        try {
            Message createdMessage = messageService.createMessage(
                    new Message(messageRequest.content(), loggedInUser));
            return new ResponseEntity<>(messageMapper.map(createdMessage), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Message could not be created");
        }
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageWithRepliesDto> getMessageWithReplies(@PathVariable Long messageId,
                                                                Authentication authentication) {
        Message message = messageService.getMessageWithRepliesById(messageId);

        if(!isEmployeeOrMessageOwner(authentication, message))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(
                new MessageWithRepliesDto(message.getId(), message.getContent(), message.getDateTime(),
                        mapToUserDto(message.getSender()), message.getStatus(),
                        mapToReplyDtos(message.getReplies())));
    }

    @GetMapping("/current")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<MessageDto>> getCurrentMessages() {
        try {
            List<Message> currentMessages = messageService.getCurrentMessagesForEmployees();
            return ResponseEntity.ok(messageMapper.mapAll(currentMessages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{messageId}/reply")
    public ResponseEntity<MessageDto> replyToMessage(
            @PathVariable Long messageId,
            @RequestBody MessageRequest replyRequest,
            Authentication authentication
    ) {
        try {
            Message parentMessage = messageService.getMessageWithRepliesById(messageId);
            User loggedInUser = userService.getUserByUsername(authentication.getName());

            if(!isEmployeeOrMessageOwner(authentication, parentMessage))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            Message repliedMessage = messageService.replyToMessage(parentMessage,
                    new Reply(replyRequest.content(), loggedInUser));
            return new ResponseEntity<>(messageMapper.map(repliedMessage), HttpStatus.CREATED);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Message could not be replied");
        }
    }

    private boolean isEmployeeOrMessageOwner(Authentication authentication, Message message) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        return authentication.getAuthorities().stream()
                .anyMatch(auth -> "EMPLOYEE".equals(auth.getAuthority())) ||
                message.getSender().getId().equals(userId);
    }

    private UserDto mapToUserDto(User sender) {
        return new UserDto(sender.getId(), sender.getUsername(), sender.getFirstName(), sender.getLastName());
    }

    private List<ReplyDto> mapToReplyDtos(List<Reply> replies) {
        List<ReplyDto> replyDtos = new ArrayList<>();
        for (Reply r: replies) {
            User replySender = r.getSender();
            replyDtos.add(new ReplyDto(r.getId(), r.getContent(), r.getDateTime(),
                    new UserDto(replySender.getId(), replySender.getUsername(),
                            replySender.getFirstName(), replySender.getLastName())));
        }
        return replyDtos;
    }
}
