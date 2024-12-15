package com.epa.m.multi.jmprest.repository;

import com.epa.m.multi.jmprest.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements UserDao {

    private List<User> users = new ArrayList<>();

    public void init(List<User> users) {
        this.users = users;
    }

    @Override
    public User save(User user) {
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLastName(String lastName) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User update(User user) {
        return user;
    }

    @Override
    public void delete(User user) {
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean existsById(int id) {
        return false;
    }
}
