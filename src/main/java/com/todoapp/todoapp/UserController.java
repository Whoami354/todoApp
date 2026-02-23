package com.todoapp.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    private ResponseEntity<User> register(@RequestBody User newUser)
    {
        var savedUser = userRepository.save(newUser);

        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }
}
