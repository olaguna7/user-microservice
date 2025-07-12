package com.oscar.usermicroservice.service;

import com.oscar.usermicroservice.dto.UserCreateDTO;
import com.oscar.usermicroservice.dto.UserDTO;
import com.oscar.usermicroservice.entity.User;
import com.oscar.usermicroservice.mapper.UserMapper;
import com.oscar.usermicroservice.mapper.UserCreateMapper;
import com.oscar.usermicroservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        return UserMapper.toDTOList(userRepository.findAll());
    }

    public UserDTO findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return UserMapper.toDTO(user);
    }

    public UserCreateDTO createUser(UserCreateDTO userDTO) {
        return UserCreateMapper.toDTO(userRepository.save(UserCreateMapper.toEntity(userDTO)));
    }

    public UserCreateDTO modifyUser(UserCreateDTO userCreateDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.setUsername(userCreateDTO.getUsername());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(userCreateDTO.getPassword());
        return UserCreateMapper.toDTO(userRepository.save(user));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserDTO> findUsersByRoleId(Long roleId) {
        return UserMapper.toDTOList(userRepository.findByRolesRoleId(roleId));
    }
}
