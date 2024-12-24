package com.example.finance.util;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/*
 * author: Prasanna
 * 
 * BCryptPasswordEncoderUtil is a utility class that provides methods for password encoding and matching.
 * It uses BCryptPasswordEncoder from Spring Security to handle password hashing and verification.
 */
@Component
public class BCryptPasswordEncoderUtil {

    /*
     * author: Prasanna
     * 
     * Provides a BCryptPasswordEncoder bean.
     * 
     * @return BCryptPasswordEncoder instance for encoding passwords
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * author: Prasanna
     * 
     * Encodes a plain text password using BCrypt algorithm.
     * 
     * @param password the plain text password to be encoded
     * @return encoded password as a string
     */
    public String encodePassword(String password) {
        return bCryptPasswordEncoder().encode(password);
    }

    /*
     * author: Prasanna
     * 
     * Checks if a raw password matches the encoded password.
     * 
     * @param rawPassword the plain text password to be compared
     * @param encodedPassword the encoded password to compare against
     * @return true if the passwords match, false otherwise
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }
}
