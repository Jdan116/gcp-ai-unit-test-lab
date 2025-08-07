package com.example.lab.service;

import com.example.lab.model.User;
import com.example.lab.model.UserDto;
import com.example.lab.repository.UserRepository;
import com.example.lab.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        return userRepository.save(new User(dto.getName(), dto.getEmail()));
    }
}