package com.epa.m.multi.jmprest.repository;

import com.epa.m.multi.jmprest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface {

    User save(User user);
    Optional<User> findById(int id);
    Optional<User> findByLastName(String lastName);
    List<User> findAll();
    User update(User user);
    void delete(User user);
    int count();
    boolean existsById(int id);
}
