package com.example.finance.controller;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finance.entity.AuthUser;
import com.example.finance.entity.Category;
import com.example.finance.services.CategoryService;

/*
 * author: Prasanna
 * 
 * CategoryController handles CRUD operations for categories.
 * It interacts with the CategoryService to perform operations related to categories.
 */
@RestController
@RequestMapping("/api/user")
public class CategoryController {

    // Injected CategoryService for handling category operations
    @Autowired
    private CategoryService categoryService;

    /*
     * author: Prasanna
     * 
     * Adds a new category.
     * 
     * @param category the Category object containing category details
     * @return ResponseEntity containing the created category or an error message
     */
    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try {
            ResponseEntity<Category> savedCategory = categoryService.addCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding category");
        }
    }

    /*
     * author: Prasanna
     * 
     * Retrieves a category by its ID.
     * 
     * @param id the ID of the category to be fetched
     * @return ResponseEntity containing the fetched category or an error message
     */
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        try {
            List<Category> categories = categoryService.getCategories();
            System.out.println(categories);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching categories");
        }
    }
    

    /*
     * author: Prasanna
     * 
     * Updates an existing category by its ID.
     * 
     * @param id the ID of the category to be updated
     * @param category the Category object containing updated details
     * @return ResponseEntity containing the updated category or an error message
     */
    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating category");
        }
    }

    /*
     * author: Prasanna
     * 
     * Deletes a category by its ID.
     * 
     * @param id the ID of the category to be deleted
     * @return ResponseEntity with status OK if successful, or an error message
     */
    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting category");
        }
    }

    /*
     * author: Prasanna
     * 
     * Retrieves all categories associated with the authenticated user.
     * 
     * @return ResponseEntity containing the list of categories
     */
//    @GetMapping("/fetchAll")
//    public ResponseEntity<List<Category>> getAllCategories() {      
//       List<Category> categories = categoryService.getCategoriesForAuthenticatedUser();
//       return ResponseEntity.ok(categories);
//    }
//    
//    @GetMapping("/authenticated/categories")
//    public List<Category> getCategories(Authentication authentication) {
//        // Retrieve the authenticated user's ID from the Authentication object
//        Long userId = ((AuthUser) authentication.getPrincipal()).getId();
//
//        // Call the service to get categories for the authenticated user
//        return categoryService.getCategoriesByUserId(userId);
//    }
}
