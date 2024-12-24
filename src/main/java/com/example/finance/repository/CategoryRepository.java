package com.example.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.finance.entity.Category;

/*
 * author: Prasanna
 * 
 * CategoryRepository is a JPA repository interface for managing Category entities.
 * It extends JpaRepository to provide basic CRUD operations.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /*
     * author: Prasanna
     * 
     * Retrieves all categories associated with a specific user.
     * 
     * @param id the ID of the user whose categories are to be retrieved
     * @return a list of categories associated with the specified user
     */
    @Query("SELECT c FROM Category c WHERE c.user.id = :id")
    List<Category> findAllByUser_Id(@Param("id")Long id);

    // List<Category> findByUser_Username(String username);
    

}
