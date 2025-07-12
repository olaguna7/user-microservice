package com.oscar.usermicroservice.mapper;

import com.oscar.usermicroservice.dto.UserCreateDTO;
import com.oscar.usermicroservice.entity.User;

public class UserCreateMapper {

    public static UserCreateDTO toDTO(User user) {
        UserCreateDTO userDTO = new UserCreateDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    public static User toEntity(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(userCreateDTO.getPassword());
        return user;
    }

}
