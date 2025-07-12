package com.oscar.usermicroservice.mapper;

import com.oscar.usermicroservice.dto.UserDTO;
import com.oscar.usermicroservice.entity.User;

import java.util.List;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream().map(UserMapper::toDTO).toList();
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }

}
