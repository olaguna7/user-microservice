package com.oscar.usermicroservice.controller;

import com.oscar.usermicroservice.dto.UserCreateDTO;
import com.oscar.usermicroservice.dto.UserDTO;
import com.oscar.usermicroservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller implementation for CRUD of User entity
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Finds all users (if any)
     *
     * @return a list of all the Users converted to DTOs
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Finds the user for a specific userId
     *
     * @param userId the identifier of the user
     * @return the user for the specified id, else throws an exception
     */
    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable("userId") Long userId) {
        return userService.findById(userId);
    }

    /**
     * Creates a new user
     *
     * @param userDTO the essential data of the user
     * @return the DTO of the user created
     */
    @PostMapping
    public ResponseEntity<UserCreateDTO> createUser(@Valid @RequestBody UserCreateDTO userDTO) {
        UserCreateDTO user = userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Updates the information of a user
     *
     * @param userDTO the user complete information
     * @param userId the identifier of the user
     * @return the complete new data of the user
     */
    @PutMapping("/{userId}")
    public UserCreateDTO modifyUser(@Valid @RequestBody UserCreateDTO userDTO, @PathVariable("userId") Long userId) {
        return userService.modifyUser(userDTO, userId);
    }

    /**
     * Deletes a user
     *
     * @param userId the identifier of the user
     * @return the info of the user deleted
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
