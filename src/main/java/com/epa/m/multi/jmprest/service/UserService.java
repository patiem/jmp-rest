package com.epa.m.multi.jmprest.service;

import com.epa.m.multi.jmprest.model.User;
import com.epa.m.multi.jmprest.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User product) {
        return userRepository.save(product);
    }

    public void deleteUser(User p) {
        userRepository.delete(p);
    }

    public void deleteAllUsers() {
        userRepository.deleteAllInBatch();
    }

    @Transactional(readOnly = true)
    public long countUsers() {
        return userRepository.count();
    }

    public void initializeDatabase() {
    }
}
