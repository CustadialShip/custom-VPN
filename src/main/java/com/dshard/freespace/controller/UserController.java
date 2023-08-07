package com.dshard.freespace.controller;

import com.dshard.freespace.model.RequestFormUser;
import com.dshard.freespace.model.User;
import com.dshard.freespace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam int limit) {
        return ResponseEntity.ok().body(userService.getUsers(limit));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUsers(@PathVariable String userId) {
        Optional<User> user = userService.getUser(userId);
        return user.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody RequestFormUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
}
