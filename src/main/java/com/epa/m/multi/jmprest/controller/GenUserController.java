package com.epa.m.multi.jmprest.controller;

import com.epa.m.multi.jmprest.model.GenUserResponse;
import com.epa.m.multi.jmprest.service.UserGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenUserController {

    @Autowired
    UserGeneratorService userGeneratorService;

    @GetMapping("/create")
    public GenUserResponse create() {
        return userGeneratorService.createUsers();
    }
}
