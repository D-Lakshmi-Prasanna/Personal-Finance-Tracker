package com.example.finance.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*
 * author: Prasanna
 *
 * CustomUserDetails is a wrapper around the AuthUser entity used by Spring Security 
 * to convert the AuthUser entity into a UserDetails object.
 */
public class CustomUserDetails implements UserDetails {
    private final AuthUser user;

    // Constructor to initialize with an AuthUser object
    public CustomUserDetails(AuthUser user) {
        this.user = user;
    }

    /*
     * Returns the authorities granted to the user.
     * 
     * Here, it maps the user's role to a `SimpleGrantedAuthority` object which is 
     * required by Spring Security.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
    }

    /*
     * Returns the password of the user.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /*
     * Returns the username (email) of the user.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /*
     * Returns `true` if the account has not expired; `false` otherwise.
     * 
     * This method can be modified based on business logic to reflect whether the 
     * account is expired or not.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Modify based on your business logic
    }

    /*
     * Returns `true` if the account is not locked; `false` otherwise.
     * 
     * This method can be modified based on business logic to reflect whether the 
     * account is locked or not.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Modify based on your business logic
    }

    /*
     * Returns `true` if the credentials (password) have not expired; `false` otherwise.
     * 
     * This method can be modified based on business logic to reflect whether the 
     * credentials are expired or not.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modify based on your business logic
    }

    /*
     * Returns `true` if the user is enabled; `false` otherwise.
     * 
     * Assuming the `AuthUser` entity has an `isEnabled` method that returns the 
     * enabled status of the user.
     */
    @Override
    public boolean isEnabled() {
        return true; // Assuming the AuthUser entity has an `isEnabled` method
    }
}
