package com.example.kanban.service;

import com.example.kanban.model.KanbanUser;
import com.example.kanban.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public KanbanUser saveUser (KanbanUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public KanbanUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}