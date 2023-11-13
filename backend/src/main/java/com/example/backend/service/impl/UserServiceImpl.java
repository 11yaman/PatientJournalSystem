package com.example.backend.service.impl;

import com.example.backend.exception.DuplicatedUserInfoException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.UserDetailsImpl;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new DuplicatedUserInfoException();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }

    @Override
    public boolean isEmployee(Authentication authentication) {
        return authentication!=null &&  authentication.getAuthorities().stream()
                .anyMatch(auth -> "EMPLOYEE".equals(auth.getAuthority()));
    }

    @Override
    public boolean isEmployeeOrResourceOwner(Authentication authentication, Long userId) {
        return isEmployee(authentication) ||
                userId.equals(getAuthenticatedUserId(authentication));
    }
}
