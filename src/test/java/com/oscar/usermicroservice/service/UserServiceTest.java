package com.oscar.usermicroservice.service;

import com.oscar.usermicroservice.dto.UserCreateDTO;
import com.oscar.usermicroservice.dto.UserDTO;
import com.oscar.usermicroservice.entity.User;
import com.oscar.usermicroservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    @DisplayName("findById should return the UserDTO when user exists")
    void shouldReturnUserDTO_whenUserExists() {
        UserRepository mockRepository = mock(UserRepository.class);
        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
        UserService userService = new UserService(mockRepository, mockEncoder);

        User user = new User(1L, "User1", "user@user.com", "12345");

        when(mockRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.findById(1L);
        assertThat(userDTO).isEqualTo(new UserDTO("User1", "user@user.com"));

        verify(mockRepository).findById(1L);
    }

    @Test
    @DisplayName("findById should throw an EntityNotFoundException when user does not exist")
    void shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        UserRepository mockRepository = mock(UserRepository.class);
        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
        UserService userService = new UserService(mockRepository, mockEncoder);

        when(mockRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(1L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(mockRepository).findById(1L);
    }

    @Test
    void shouldCreateUser_whenDataIsValid() {
        UserRepository mockRepository = mock(UserRepository.class);
        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
        UserService userService = new UserService(mockRepository, mockEncoder);

        UserCreateDTO userToCreate = new UserCreateDTO("user2", "user2@user.com", "password2");

        User userEntity = new User(1L, "user2", "user2@user.com", "password2");
        when(mockRepository.save(any(User.class))).thenReturn(userEntity);

        UserCreateDTO result = userService.createUser(userToCreate);

        assertThat(result).isEqualTo(userToCreate); // O compara campo a campo si no tienes equals en el DTO
    }

    @Test
    void shouldDeleteUser_whenUserExists() {
        UserRepository mockRepository = mock(UserRepository.class);
        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
        UserService userService = new UserService(mockRepository, mockEncoder);

        User user = new User(1L, "user1", "user1@user.com", "password1");

        userService.deleteUser(1L);
        verify(mockRepository).deleteById(1L);
    }

    @Test
    void shouldThrowEmptyResultDataAccessException_whenUserDoesNotExist() {
        UserRepository mockRepository = mock(UserRepository.class);
        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
        UserService userService = new UserService(mockRepository, mockEncoder);

        doThrow(new EmptyResultDataAccessException(1)).when(mockRepository).deleteById(1L);

        assertThatThrownBy(() -> userService.deleteUser(1L))
                .isInstanceOf(EmptyResultDataAccessException.class);

        verify(mockRepository).deleteById(1L);
    }
}
