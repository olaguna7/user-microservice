package com.oscar.usermicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oscar.usermicroservice.dto.UserCreateDTO;
import com.oscar.usermicroservice.dto.UserDTO;
import com.oscar.usermicroservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnAllUsers_whenGetAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUser_whenGetById() throws Exception {
        UserDTO dto = new UserDTO("user", "mail@mail.com");
        when(userService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("mail@mail.com"));
    }

    @Test
    void shouldReturnNotFound_whenGetByIdNotFound() throws Exception {
        when(userService.findById(200L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/users/200"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequest_whenCreateUserWithInvalidEmail() throws Exception {
        UserCreateDTO dto = new UserCreateDTO("user", "user@user", "123");
        when(userService.createUser(any(UserCreateDTO.class)))
                .thenThrow(new DataIntegrityViolationException("Error in email format"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenCreateUserWithNullField() throws Exception {
        // DTO with null username
        UserCreateDTO dto = new UserCreateDTO();
        dto.setEmail("hola@hola.com");
        dto.setPassword("12345");

        when(userService.createUser(any(UserCreateDTO.class)))
                .thenThrow(new DataIntegrityViolationException("Null field cannot be inserted"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

}