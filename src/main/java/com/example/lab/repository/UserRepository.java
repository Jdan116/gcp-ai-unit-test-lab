package com.example.lab.repository;

import org.springframework.stereotype.Repository;
import com.example.lab.model.User;

@Repository
public class UserRepository {

    public boolean existsByEmail(String email) {
        // Simulate an email existence check
        return "exists@example.com".equalsIgnoreCase(email);
    }

    public User save(User user) {
        // Simulate saving the user
        return user;
    }
}