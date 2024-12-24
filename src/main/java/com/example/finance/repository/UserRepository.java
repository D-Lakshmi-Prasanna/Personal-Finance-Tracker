package com.example.finance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.finance.entity.AuthUser;

/*
 * author: Prasanna
 * 
 * UserRepository is a JPA repository interface for managing AuthUser entities.
 * It extends JpaRepository to provide basic CRUD operations and includes a custom method to find a user by their email.
 */
public interface UserRepository extends JpaRepository<AuthUser, Long> {

    /*
     * author: Prasanna
     * Finds an AuthUser by their email.
     * 
     * @param email the email of the user
     * @return an Optional containing the AuthUser if found, or empty otherwise
     */
    Optional<AuthUser> findByEmail(String email);
}
