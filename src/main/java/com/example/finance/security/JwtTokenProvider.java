package com.example.finance.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * author: Prasanna
 * 
 * JwtTokenProvider provides methods to generate and validate JWT tokens.
 * It uses the secret key and expiration time defined in the application properties.
 */
@Component
public class JwtTokenProvider {
    
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration-time}")
    private long EXPIRATION_TIME; // 1 day

    /*
     * author: Prasanna
     * 
     * Generates a JWT token for the given email.
     * 
     * @param email the email to include in the token's subject
     * @return the generated JWT token
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /*
     * author: Prasanna
     * 
     * Extracts the email from the JWT token.
     * 
     * @param token the JWT token
     * @return the email extracted from the token
     */
    public String extractEmail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /*
     * author: Prasanna
     * 
     * Validates the JWT token.
     * 
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
