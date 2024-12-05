package com.epa.m.multi.jmprest.controller;

import com.epa.m.multi.jmprest.model.UserGen;
import com.epa.m.multi.jmprest.service.UserGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    final UserGenService userGenService;

    public UserController(UserGenService userGenService) {
        this.userGenService = userGenService;
    }

    @GetMapping("/getAll")
    public List<UserGen> create() {
        return userGenService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/hello2")
    public String sayHelloFull(@RequestParam String name) { //this way name must be provided in URL
        return "welcome";
    }
}
