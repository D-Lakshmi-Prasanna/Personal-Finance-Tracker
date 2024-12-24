package com.example.finance.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.finance.entity.CustomUserDetails;
import com.example.finance.repository.UserRepository;
import com.example.finance.security.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * author: Prasanna
 * 
 * JwtFilter is a Spring Security filter that intercepts every HTTP request to verify and authenticate JWT tokens.
 * The filter validates the JWT token and sets the corresponding authentication in the security context if the token is valid.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    // Injected JwtTokenProvider and UserRepository beans
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    /*
     * author: Prasanna
     * 
     * Processes each incoming HTTP request.
     * 
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param chain the FilterChain object for the next filter in the chain
     * 
     * @throws ServletException if an error occurs during the processing of the request
     * @throws IOException if an I/O error occurs during the processing of the request
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extract token part

            // Extract email from token
            String email = jwtTokenProvider.extractEmail(token);

            // Validate token and email
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userOptional = userRepository.findByEmail(email);

                if (userOptional.isPresent() && jwtTokenProvider.validateToken(token)) {
                    // Wrap User entity in CustomUserDetails
                    UserDetails userDetails = new CustomUserDetails(userOptional.get());

                    // Set authentication for the user
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response); // Continue to the next filter in the chain
    }
}
