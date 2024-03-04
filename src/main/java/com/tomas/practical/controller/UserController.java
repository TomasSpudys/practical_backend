package com.tomas.practical.controller;

import com.tomas.practical.model.LoginRequest;
import com.tomas.practical.model.RegisterRequest;
import com.tomas.practical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        // Assuming you have a method in the UserService for user authentication
        if (userService.login(loginRequest.getUsername(), loginRequest.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // POST endpoint for user registration
    @PostMapping(value="/register", consumes = "application/json")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registrationRequest) {
        // Assuming you have a method in the UserService for user registration
        boolean registrationSuccess = userService.createUser(registrationRequest.getUsername(), registrationRequest.getPassword());
        if (registrationSuccess) {
            return ResponseEntity.ok("Registration successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this username already exists");
        }
    }
}