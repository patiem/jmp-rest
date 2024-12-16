package com.epa.m.multi.jmprest.controller;

import com.epa.m.multi.jmprest.model.User;
import com.epa.m.multi.jmprest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return service.findAllUsers();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.of(service.findUserById(id));
    }

//    @GetMapping(params = "min")
//    public List<User> getUsersByMinPrice(@RequestParam(defaultValue = "0.0") double min) {
//        if (min < 0) throw new UserMinimumPriceException(min);
//        return service.findAllUsersByMinPrice(min);
//    }

    @GetMapping("count")
    public long getUserCount() {
        return service.countUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User u = service.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(u.getId())
                .toUri();
        return ResponseEntity.created(location).body(u);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return service.findUserById(id)
                .map(u -> {
                    u.setFirstName(user.getFirstName());
                    u.setLastName(user.getLastName());
                    u.setAge(user.getAge());
                    return ResponseEntity.ok(service.saveUser(u));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return service.findUserById(id)
                .map(p -> {
                    service.deleteUser(p);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllUsers() {
        service.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }

}
