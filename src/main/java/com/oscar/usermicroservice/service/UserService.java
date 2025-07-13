package com.oscar.usermicroservice.service;

import com.oscar.usermicroservice.dto.UserCreateDTO;
import com.oscar.usermicroservice.dto.UserDTO;
import com.oscar.usermicroservice.entity.User;
import com.oscar.usermicroservice.exception.EmailExistsException;
import com.oscar.usermicroservice.exception.UsernameExistsException;
import com.oscar.usermicroservice.mapper.UserMapper;
import com.oscar.usermicroservice.mapper.UserCreateMapper;
import com.oscar.usermicroservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAll() {
        return UserMapper.toDTOList(userRepository.findAll());
    }

    public UserDTO findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return UserMapper.toDTO(user);
    }

    public UserCreateDTO createUser(UserCreateDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailExistsException("The email " + userDTO.getEmail() + " already exists.");
        } else if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UsernameExistsException("The username " + userDTO.getEmail() + " already exists.");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return UserCreateMapper.toDTO(userRepository.save(UserCreateMapper.toEntity(userDTO)));
    }

    public boolean validateCredentials(String usernameOrEmail, String rawPassword) {
        User user = userRepository.findUserByUsername(usernameOrEmail)
                .orElse(userRepository.findUserByEmail(usernameOrEmail).orElse(null));

        return user != null && passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public UserCreateDTO modifyUser(UserCreateDTO userCreateDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.setUsername(userCreateDTO.getUsername());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        return UserCreateMapper.toDTO(userRepository.save(user));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
