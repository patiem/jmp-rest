package com.epa.m.multi.jmprest.repository;

import com.epa.m.multi.jmprest.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void init(List<User> users) {
        this.users = users;
    }



}
