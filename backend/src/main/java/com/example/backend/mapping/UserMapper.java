package com.example.backend.mapping;

import com.example.backend.dto.response.UserDto;
import com.example.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper implements StrategyMapper<User, UserDto> {

    @Override
    public UserDto map(User source) {
        return new UserDto(source.getId(), source.getUsername(),
                source.getFirstName(),source.getLastName(),
                source.getBirthDate(), source.getRole().name());
    }

    @Override
    public List<UserDto> mapAll(List<User> source) {
        List<UserDto> UserDtos = new ArrayList<>();
        for (User p:
                source) {
            UserDtos.add(map(p));
        }
        return UserDtos;
    }
}
