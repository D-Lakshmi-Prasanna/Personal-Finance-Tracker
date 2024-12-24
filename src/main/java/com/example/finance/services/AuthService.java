package com.example.finance.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.finance.entity.AuthUser;
import com.example.finance.exceptions.EmailAlreadyExistsException;
import com.example.finance.exceptions.UserNotFoundException;
import com.example.finance.exceptions.WrongUserTypeException;
import com.example.finance.repository.UserRepository;
import com.example.finance.security.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

/*
 * author: Prasanna
 * 
 * AuthService provides business logic for user authentication and registration.
 * It interacts with the UserRepository to manage user data and uses the JwtTokenProvider for generating authentication tokens.
 */
@Slf4j
@Service
public class AuthService {

    // UserRepository for accessing user data from the database
    @Autowired
    private UserRepository userRepository;

    // BCryptPasswordEncoder for password encryption
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // JwtTokenProvider for creating JWT tokens
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /*
     * author: Prasanna
     * 
     * Registers a new user by encrypting the user's password and saving the user to the database.
     * 
     * @param user the AuthUser object containing registration details
     * @return the newly registered AuthUser
     */
    public AuthUser registerUser(AuthUser user) {
    	if (user.getRole() == null) {
            log.info("Role is not provided or empty");
            throw new WrongUserTypeException("Role cannot be null or empty");
        }

        switch (user.getRole()) {
            case ADMIN:
                log.info("You cannot register because you are admin");
                throw new WrongUserTypeException("Wrong user type");

            case USER:
            	if (isEmailAlreadyRegistered(user.getEmail())) {
                    throw new EmailAlreadyExistsException("Email already exists.");
                }
                // Encrypt the user's password before saving it to the database
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);

            default:
                log.info("Please choose the type either USER or ADMIN");
                throw new WrongUserTypeException("Wrong user type");
        }
    }
    public boolean isEmailAlreadyRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    
    /*
     * author: Prasanna
     * 
     * Authenticates a user and returns a JWT token if the credentials are valid.
     * 
     * @param email the email of the user attempting to login
     * @param password the password provided by the user
     * @return the generated JWT token for the authenticated user
     * @throws UserNotFoundException if the user with the given email does not exist
     * @throws BadCredentialsException if the provided password is incorrect
     */
    public String login(String email, String password) {
        // Find the user by email
        AuthUser user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        
        // Check if the provided password matches the stored password
        if (passwordEncoder.matches(password, user.getPassword())) {
            // Generate a JWT token for the authenticated user
            return jwtTokenProvider.generateToken(user.getEmail());
        } else {
            // Throw an exception if the credentials are invalid
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
