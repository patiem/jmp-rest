package com.epa.m.multi.jmprest.service;

import com.epa.m.multi.jmprest.model.User;
import com.epa.m.multi.jmprest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }
}