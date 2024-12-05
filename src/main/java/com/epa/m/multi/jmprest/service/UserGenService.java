package com.epa.m.multi.jmprest.service;

import com.epa.m.multi.jmprest.model.UserGen;
import com.epa.m.multi.jmprest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserGenService {

    private final UserRepository userRepository;

    public UserGenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserGen> getAllUsers() {
        return Collections.emptyList();
//        return userRepository.findAll();
    }
}
