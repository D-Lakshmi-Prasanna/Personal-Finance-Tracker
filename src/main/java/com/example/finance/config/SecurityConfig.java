package com.example.finance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

/*
 * author: Prasanna
 * 
 * SecurityConfig class is responsible for configuring the security settings of the application.
 * It defines beans for password encoding, authentication management, and the security filter chain.
 */
@Configuration
public class SecurityConfig {

    // JwtFilter to be used in the security filter chain
    @Autowired
    private JwtFilter jwtFilter;

    /*
     * author: Prasanna
     * 
     * Provides a BCryptPasswordEncoder bean for password encoding.
     * 
     * @return a PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * author: Prasanna
     * 
     * Provides an AuthenticationManager bean.
     * 
     * @param configuration the AuthenticationConfiguration
     * @return the AuthenticationManager bean
     * @throws Exception if an error occurs during the creation of the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /*
     * author: Prasanna
     * 
     * Configures the security settings for the HTTP requests.
     * 
     * @param http the HttpSecurity object used to configure the security settings
     * @return the SecurityFilterChain bean
     * @throws Exception if an error occurs during the configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable CSRF protection
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/user/**").hasAuthority("USER")
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated() // All other requests must be authenticated
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter to the security chain

        return http.build();
    }
    
    
}
