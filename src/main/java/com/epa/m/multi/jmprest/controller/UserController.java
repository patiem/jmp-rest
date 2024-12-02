package com.epa.m.multi.jmprest.controller;

import com.epa.m.multi.jmprest.model.GenUserResponse;
import com.epa.m.multi.jmprest.model.User;
import com.epa.m.multi.jmprest.service.UserGeneratorService;
import com.epa.m.multi.jmprest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<User> create() {
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/hello2")
    public String sayHelloFull(@RequestParam String name) { //this way name must be provided in URL
        return "welcome";
    }
}
