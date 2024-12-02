package com.epa.m.multi.jmprest.service;

import com.epa.m.multi.jmprest.model.User;
import com.epa.m.multi.jmprest.model.GenUserResponse;
import com.epa.m.multi.jmprest.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserGeneratorService {
    private final WebClient webClient;
    private final UserRepository repository;

    private final List<User> usersList = new ArrayList<>();

    public UserGeneratorService(UserRepository repository) {
        this.repository = repository;
        webClient = WebClient.create("https://randomuser.me");
    }

    public GenUserResponse createUsers() {
        GenUserResponse response = webClient.get()
                .uri("api/?results=10&inc=name,gender,nat,email,id&noinfo").retrieve()
                .bodyToMono(GenUserResponse.class)
                .block();
        usersList.addAll(Optional.ofNullable(response.results()).orElse(Collections.emptyList()));
        repository.init(usersList);
        return response;

    }

}
