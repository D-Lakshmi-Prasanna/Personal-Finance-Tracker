package com.example.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.finance.entity.AuthUser;
import com.example.finance.entity.Category;
import com.example.finance.entity.Transaction;
import com.example.finance.exceptions.CustomExceptions;
import com.example.finance.exceptions.EntityNotFoundException;
import com.example.finance.exceptions.UserNotFoundException;
import com.example.finance.repository.CategoryRepository;
import com.example.finance.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/*
 * author: Prasanna
 * 
 * CategoryService provides business logic for managing categories.
 * It interacts with the CategoryRepository to perform CRUD operations on categories, 
 * and with the UserRepository to link categories to users.
 */
@Slf4j
@Service
public class CategoryService {
	
	// Injected UserRepository for managing user data
	@Autowired
	private UserRepository userRepository;
	
	// Injected CategoryRepository for managing category data
	@Autowired
	private CategoryRepository categoryRepository;

	/*
	 * author: Prasanna
	 * 
	 * Adds a new category to the user's list.
	 * 
	 * @param category the Category object containing the category details
	 * @return the newly added Category
	 * @throws UserNotFoundException if the user associated with the category does not exist
	 */
	public ResponseEntity<Category> addCategory(Category category) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        try {
        	AuthUser user=getUserByEmail(email);
        	category.setUser(user);
        	categoryRepository.save(category);
        	return ResponseEntity.status(HttpStatus.CREATED).body(category);
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
	}
		

	/*
	 * author: Prasanna
	 * 
	 * Retrieves a specific category by its ID.
	 * 
	 * @param id the ID of the category
	 * @return an Optional containing the Category if found, otherwise empty
	 * @throws EntityNotFoundException if the category does not exist
	 */
	public List<Category> getCategories() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AuthUser user=getUserByEmail(email);
        Long id=user.getId();
        System.out.println(id);
		return categoryRepository.findAllByUser_Id(id);
	}
	
	/*
	 * author: Prasanna
	 * 
	 * Updates an existing category.
	 * 
	 * @param categoryId the ID of the category to be updated
	 * @param updatedCategory the Category object containing the updated details
	 * @return the updated Category
	 * @throws EntityNotFoundException if the category to be updated does not exist
	 */
	public Category updateCategory(Long categoryId, Category updatedCategory) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AuthUser user=getUserByEmail(email);
        Long id=user.getId();
        Category category=categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Transaction not found"));;
        Long userId=category.getUser().getId();
        if(id==userId) {
        	category.setName(updatedCategory.getName());
    		category.setDescription(updatedCategory.getDescription());
    		return categoryRepository.save(category);
        }
        else {
        	log.info("You cannot change this category because this category is not belongs to you");
        	throw new CustomExceptions("You cannot change this category because this category is not belongs to you");
        }
	}
	
	/*
	 * author: Prasanna
	 * 
	 * Deletes a category by its ID.
	 * 
	 * @param categoryId the ID of the category to be deleted
	 * @throws EntityNotFoundException if the category to be deleted does not exist
	 */
	public void deleteCategory(Long categoryId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AuthUser user=getUserByEmail(email);
        Long id=user.getId();
        Category category=categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Category not found"));;
        Long userId=category.getUser().getId();
        if(id==userId) {
    		categoryRepository.delete(category);
    		log.info("succefully deleted");
        }
        else {
        	log.info("You cannot delete this category because this category is not belongs to you");
        	throw new CustomExceptions("You cannot delete this category because this category is not belongs to you");
        }
		
	}
	public AuthUser getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email)); // You can use your custom exception or return null
    }


	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}
	
	/*
	 * author: Prasanna
	 * 
	 * Retrieves all categories associated with the authenticated user.
	 * 
	 * @return a List of Category objects
	 */
//	public List<Category> getCategoriesForAuthenticatedUser() {
//		AuthUser principal = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Long id = principal.getId();
//		return categoryRepository.findAllByUser_Id(id);
//	}
//	
//	public List<Category> getCategoriesByUserId(Long id) {
//        return categoryRepository.findAllByUser_Id(id);
//    }
}
