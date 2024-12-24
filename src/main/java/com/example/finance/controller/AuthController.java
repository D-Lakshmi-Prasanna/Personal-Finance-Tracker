package com.example.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finance.entity.AuthUser;
import com.example.finance.services.AuthService;

/*
 * author: Prasanna
 * 
 * AuthController handles authentication-related requests such as user registration and login.
 * It interacts with the AuthService to perform authentication and user management operations.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Injected AuthService for handling authentication logic
    @Autowired
    private AuthService authService;

    /*
     * author: Prasanna
     * 
     * Registers a new user.
     * 
     * @param user the AuthUser object containing registration details
     * @return ResponseEntity containing the newly registered user or an error message
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthUser user) {
        try {
            AuthUser newUser = authService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error registering user");
        }
    }

    /*
     * author: Prasanna
     * 
     * Authenticates a user and returns a JWT token.
     * 
     * @param user the AuthUser object containing email and password
     * @return ResponseEntity containing the JWT token or an error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthUser user) {
        try {
            String token = authService.login(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    
    // Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcmFzYW5uYUBnbWFpbC5jb20iLCJpYXQiOjE3MzQ1MDMyNDUsImV4cCI6MTczNDU4OTY0NX0.tsgYgVUsqSvNMyZ-G2m-UutTwDPkXgw94d7M6Z6V3Sh06nyUNNwmhWI9xO5RWBlmDUKRhOTaGOtoc86_6WffjQ
}
