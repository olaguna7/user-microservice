package com.oscar.usermicroservice.controller;

import com.oscar.usermicroservice.dto.UserCreateDTO;
import com.oscar.usermicroservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCreateDTO userCreateDTO) {
        boolean valid = userService.validateCredentials(userCreateDTO.getUsername(), userCreateDTO.getPassword());
        return valid ? ResponseEntity.ok("Login successful") : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

}
